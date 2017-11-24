package cz.srayayay.tierion.hashapi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.util.Hex;

import cz.srayayay.tierion.common.CredentialsFromPropertiesFile;
import cz.srayayay.tierion.common.Hashing;
import cz.srayayay.tierion.common.JsonHelper;
import cz.srayayay.tierion.common.model.BlockchainReceipt;
import cz.srayayay.tierion.hashapi.model.HashReceiptResponse;
import cz.srayayay.tierion.hashapi.model.HashRequest;
import cz.srayayay.tierion.hashapi.model.HashResponse;
import cz.srayayay.tierion.hashapi.model.LoginRequest;
import cz.srayayay.tierion.hashapi.model.LoginResponse;
import cz.srayayay.tierion.hashapi.model.RefreshRequest;
import cz.srayayay.tierion.hashapi.model.subscriptions.BlockSubscription;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import static cz.srayayay.tierion.common.Constants.DEFAULT_CONNECTION_POOL_SIZE;
import static cz.srayayay.tierion.common.Responses.validateResponse;

public class V1TierionHashApiClient implements TierionHashApiClient {

    private final Client client;
    private final AtomicReference<String> currentAccessToken = new AtomicReference<>();
    private final AtomicReference<String> currentRefreshToken = new AtomicReference<>();

    private static final String ROOT_TARGET_URL = "https://hashapi.tierion.com/v1";
    private final WebTarget rootWebTarget;
    private final WebTarget blockSubscriptionsWebTarget;
    private final ScheduledExecutorService executor;

    private final ClientRequestFilter authenticationHeaderAdder = request -> {
        if (currentAccessToken.get() != null) {
            request.getHeaders().add("Authorization", "Bearer " + currentAccessToken.get());
        }
    };

    public V1TierionHashApiClient(String username, String password, int connectionPoolSize) {
        client = new ResteasyClientBuilder()
                .maxPooledPerRoute(connectionPoolSize)
                .connectionPoolSize(connectionPoolSize)
                .build()
                .register(authenticationHeaderAdder);
        rootWebTarget = client.target(ROOT_TARGET_URL);
        blockSubscriptionsWebTarget = rootWebTarget.path("/blocksubscriptions");
        executor = Executors.newSingleThreadScheduledExecutor();
        initialLogin(username, password);
    }

    public V1TierionHashApiClient(String username, String password) {
        this(username, password, DEFAULT_CONNECTION_POOL_SIZE);
    }

    public V1TierionHashApiClient(int connectionPoolSize) {
        this(null, null, connectionPoolSize);
    }

    public V1TierionHashApiClient() {
        this(null, null, DEFAULT_CONNECTION_POOL_SIZE);
    }

    private void initialLogin(String username, String password) {
        final WebTarget target = rootWebTarget.path("/auth").path("/token");

        final String actualUsername = username != null ? username
                : CredentialsFromPropertiesFile.getUsername();
        Objects.requireNonNull(actualUsername, "No Tierion username found");
        final String actualPassword = password != null ? password
                : CredentialsFromPropertiesFile.getPassword();
        Objects.requireNonNull(actualPassword, "No Tierion password found");

        final Response response = target.request().post(Entity.json(
                new LoginRequest(actualUsername, actualPassword))
        );
        validateResponse(response, Response.Status.OK);
        LoginResponse loginInfo = response.readEntity(LoginResponse.class);
        currentAccessToken.set(loginInfo.getAccessToken());
        currentRefreshToken.set(loginInfo.getRefreshToken());
        if(loginInfo.getExpiresIn() != null && loginInfo.getExpiresIn() > 0) {
            // schedule the first token refresh, better do it a minute in advance for safety
            executor.schedule(refreshTokenTask, loginInfo.getExpiresIn() - 60, TimeUnit.SECONDS);
        }
    }

    private final Runnable refreshTokenTask = new Runnable() {
        @Override
        public void run() {
            final WebTarget target = rootWebTarget.path("/auth").path("/refresh");
            final LoginResponse loginInfo = target.request().post(
                    Entity.json(new RefreshRequest(currentRefreshToken.get()))
            ).readEntity(LoginResponse.class);
            currentAccessToken.set(loginInfo.getAccessToken());
            currentRefreshToken.set(loginInfo.getRefreshToken());
            if(loginInfo.getExpiresIn() != null && loginInfo.getExpiresIn() > 0) {
                // schedule the next token refresh, better do it a minute in advance for safety
                executor.schedule(refreshTokenTask, loginInfo.getExpiresIn() - 60, TimeUnit.SECONDS);
            }
        }
    };

    @Override
    public HashResponse submitHash(String hash) {
        final WebTarget target = rootWebTarget.path("/hashitems");
        final Response response = target.request().post(Entity.json(new HashRequest(hash)));
        validateResponse(response, Response.Status.OK);
        return response.readEntity(HashResponse.class);
    }

    @Override
    public HashResponse submitContent(byte[] text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return submitHash(Hex.encodeHex(digest.digest(text)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashResponse submitFile(Path file) throws IOException {
        return submitHash(Hashing.hashFile(file));
    }

    @Override
    public BlockchainReceipt getReceipt(String receiptId) {
        final WebTarget target = rootWebTarget.path("/receipts").path("/" + receiptId);
        final Response response = target.request().get();
        validateResponse(response, Response.Status.OK);
        final HashReceiptResponse responseEntity = response.readEntity(HashReceiptResponse.class);
        return JsonHelper.fromJson(responseEntity.getReceipt(), BlockchainReceipt.class);
    }

    @Override
    public List<BlockSubscription> getAllBlockSubscriptions() {
        final Response response = blockSubscriptionsWebTarget.request().get();
        validateResponse(response, Response.Status.OK);
        return response.readEntity(
                new GenericType<>(
                        ParameterizedTypeImpl.make(List.class, new Type[] {BlockSubscription.class}, null))
        );
    }

    @Override
    public BlockSubscription getBlockSubscription(String id) {
        final WebTarget target = blockSubscriptionsWebTarget.path("/" + id);
        final Response response = target.request().get();
        validateResponse(response, Response.Status.OK);
        return response.readEntity(BlockSubscription.class);
    }

    @Override
    public String createBlockSubscription(String callbackUrl, String label) {
        final Response response = blockSubscriptionsWebTarget.request().post(
                Entity.json(new BlockSubscription(callbackUrl, label))
        );
        validateResponse(response, Response.Status.CREATED);
        return response.readEntity(BlockSubscription.class).getId();
    }

    @Override
    public BlockSubscription updateBlockSubscription(String id, String callbackUrl, String label) {
        final WebTarget target = blockSubscriptionsWebTarget.path("/" + id);
        final Response response = target.request()
                .put(Entity.json(new BlockSubscription(callbackUrl, label)));
        validateResponse(response, Response.Status.OK);
        return response.readEntity(BlockSubscription.class);
    }

    @Override
    public void deleteBlockSubscription(String id) {
        final WebTarget target = blockSubscriptionsWebTarget.path("/" + id);
        final Response response = target.request().delete();
        try {
            validateResponse(response, Response.Status.OK);
        } finally {
            response.close();
        }
    }

    @Override
    public void close() {
        if (client != null) {
            client.close();
        }
        if (executor != null) {
            executor.shutdownNow();
        }
    }

}
