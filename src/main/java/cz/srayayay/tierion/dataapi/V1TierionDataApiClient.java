package cz.srayayay.tierion.dataapi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import cz.srayayay.tierion.common.CredentialsFromPropertiesFile;
import cz.srayayay.tierion.dataapi.model.DatastoreObject;
import cz.srayayay.tierion.dataapi.model.GetRecordsResponse;
import cz.srayayay.tierion.dataapi.model.RecordObject;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import static cz.srayayay.tierion.common.Constants.DEFAULT_CONNECTION_POOL_SIZE;
import static cz.srayayay.tierion.common.Responses.validateResponse;

public class V1TierionDataApiClient implements TierionDataApiClient {

    static final String ROOT_TARGET_URL = "https://api.tierion.com/v1";

    private final Client client;
    private final WebTarget rootWebTarget;
    private final WebTarget datastoresWebTarget;
    private final WebTarget recordsWebTarget;

    public V1TierionDataApiClient(String username, String apiKey, Integer connectionPoolSize) {
        client = new ResteasyClientBuilder()
                .maxPooledPerRoute(connectionPoolSize)
                .connectionPoolSize(connectionPoolSize)
                .build()
                .register(new AddAuthenticationHeadersFilter(username, apiKey));
        rootWebTarget = client.target(ROOT_TARGET_URL);
        datastoresWebTarget = rootWebTarget.path("/datastores");
        recordsWebTarget = rootWebTarget.path("/records");
    }

    public V1TierionDataApiClient(String username, String apiKey) {
        this(username, apiKey, DEFAULT_CONNECTION_POOL_SIZE);
    }

    public V1TierionDataApiClient() {
        this(null, null, DEFAULT_CONNECTION_POOL_SIZE);
    }

    public V1TierionDataApiClient(Integer connectionPoolSize) {
        this(null, null, connectionPoolSize);
    }

    @Override
    public List<DatastoreObject> getAllDatastores() {
        final Response response = datastoresWebTarget.request().get(Response.class);
        return response.readEntity(
                new GenericType<>(ParameterizedTypeImpl.make(List.class, new Type[] {DatastoreObject.class}, null))
        );
    }

    @Override
    public DatastoreObject getDatastore(long id) {
        final WebTarget target = datastoresWebTarget.path("/" + id);
        final Response response = target.request().get();
        validateResponse(response, Response.Status.OK);
        return response.readEntity(DatastoreObject.class);
    }

    @Override
    public DatastoreObject createDatastore(DatastoreObject datastore) {
        final Response response = datastoresWebTarget.request().post(Entity.json(datastore));
        if(response.getStatusInfo().equals(Response.Status.INTERNAL_SERVER_ERROR)) {
            // FIXME workaround for the fact that as of 2017-11-23, Tierion returns error=500 even for successful operations
            return null; // FIXME this sucks big time, now we don't know the id
        }
        validateResponse(response, Response.Status.OK);
        return response.readEntity(DatastoreObject.class);
    }

    @Override
    public void deleteDatastore(long id) {
        final WebTarget target = datastoresWebTarget.path("/" + id);
        final Response response = target.request().delete();
        try {
            if(response.getStatusInfo().equals(Response.Status.INTERNAL_SERVER_ERROR)) {
                // FIXME workaround for the fact that as of 2017-11-23, Tierion returns error=500 even for successful deletions
                return;
            }
            validateResponse(response, Response.Status.OK);
        } finally {
            response.close();
        }
    }

    @Override
    public DatastoreObject updateDatastore(long id, DatastoreObject newDatastore) {
        final WebTarget target = datastoresWebTarget.path("/" + id);
        final Response response = target.request().put(Entity.json(newDatastore));
        validateResponse(response, Response.Status.OK);
        return response.readEntity(DatastoreObject.class);
    }

    @Override
    public RecordObject getRecord(String id) {
        final Response response = recordsWebTarget.path("/" + id).request().get();
        validateResponse(response, Response.Status.OK);
        return response.readEntity(RecordObject.class);
    }

    @Override
    public GetRecordsResponse getRecords(Long datastoreId, Long page, Long pageSize, Long startDate, Long endDate) {
        final WebTarget target = recordsWebTarget
                .queryParam("datastoreId", datastoreId)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate);
        final Response response = target.request().get();
        validateResponse(response, Response.Status.OK);
        return response.readEntity(GetRecordsResponse.class);
    }

    @Override
    public RecordObject createRecord(long datastoreId, Map<String, String> data) {
        final HashMap<String, Object> mapClone = new HashMap<>(data);
        mapClone.put("datastoreId", datastoreId);

        final Response response = recordsWebTarget.request().post(Entity.entity(mapClone, "application/json"));
        validateResponse(response, Response.Status.OK);
        return response.readEntity(RecordObject.class);
    }

    @Override
    public void deleteRecord(String id) {
        final WebTarget target = recordsWebTarget.path("/" + id);
        final Response response = target.request().delete();
        try {
            validateResponse(response, Response.Status.OK);
        } finally {
            response.close();
        }

    }

    @Override
    public void close() {
        client.close();
    }

    private static class AddAuthenticationHeadersFilter implements ClientRequestFilter {

        private final String username;
        private final String apiKey;

        AddAuthenticationHeadersFilter(String username, String apiKey) {
            this.username = username != null ? username : CredentialsFromPropertiesFile.getUsername();
            Objects.requireNonNull(this.username, "No Tierion username found");
            this.apiKey = apiKey != null ? apiKey : CredentialsFromPropertiesFile.getApiKey();
            Objects.requireNonNull(this.apiKey, "No Tierion apiKey found");
        }

        @Override
        public void filter(ClientRequestContext request) throws IOException {
            request.getHeaders().add("X-Username", username);
            request.getHeaders().add("X-Api-Key", apiKey);
        }

    }
}

