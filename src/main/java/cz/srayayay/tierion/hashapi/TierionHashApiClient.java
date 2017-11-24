package cz.srayayay.tierion.hashapi;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import cz.srayayay.tierion.hashapi.model.HashResponse;
import cz.srayayay.tierion.common.model.BlockchainReceipt;
import cz.srayayay.tierion.hashapi.model.subscriptions.BlockSubscription;

public interface TierionHashApiClient extends AutoCloseable {

	/**
	 * Submits a hash to the blockchain.
	 * @param hash A hex-encoded SHA-256 hash.
	 */
	HashResponse submitHash(String hash);

	/**
	 * Submits an item for hashing, as a byte array. The SHA-256 will be computed automatically
	 * from the byte array and will be submitted to the blockchain.
	 * @param text Content to be hashed.
	 */
	HashResponse submitContent(byte[] text);

	/**
	 * Submits a File from the filesystem for hashing. The SHA-256 hash will be computed automatically
	 * and will be submitted to the blockchain.
	 * @param file Path to the file which should be hashed and the hash submitted.
	 */
    HashResponse submitFile(Path file) throws IOException;

    /**
	 * Retrieves a receipt of a hash item. This will only work for hash items which are already processed and placed on the blockchain.
	 * @param receiptId ID of the receipt to be retrieved.
	 * @return Representation of the requested blockchain receipt.
	 * @throws cz.srayayay.tierion.common.TierionException If no receipt with the specified ID exists.
	 */
	BlockchainReceipt getReceipt(String receiptId);

	/**
	 * Retrieves a list of all block subscriptions bound to the user's account.
	 * @return A list of all block subscriptions.
	 */
	List<BlockSubscription> getAllBlockSubscriptions();

	/**
	 * Retrieves data about a block subscription specified by id.
	 * @param id The block subscription ID.
	 * @return Representation of the requested block subscription.
	 * @throws cz.srayayay.tierion.common.TierionException If no subscription with the requested ID is found.
	 */
	BlockSubscription getBlockSubscription(String id);

	/**
	 * Creates a new block subscription using the specified callbackUrl and label.
	 * @param callbackUrl The callback URL for this block subscription.
	 * @param label The label of this block subscription.
	 * @return The id of the newly created block subscription.
	 * TODO: a tool for computing the HMAC-SHA256 signature (https://tierion.com/docs/hashapi#api-blocksubscriptions)
	 */
	String createBlockSubscription(String callbackUrl, String label);

	/**
	 * Updates an existing block subscription.
	 * @param id The ID of the block subscription to be updated.
	 * @param callbackUrl The new callback URL. If null, then the current callback URL will remain unchanged.
	 * @param label The new label. If null, then the current label will remain unchanged.
	 * @throws cz.srayayay.tierion.common.TierionException If no subscription with the requested ID is found.
	 */
	BlockSubscription updateBlockSubscription(String id, String callbackUrl, String label);

	/**
	 * Deletes an existing block subscription with the specified id.
	 * @param id The ID of the block subscription to be deleted.
	 * @throws cz.srayayay.tierion.common.TierionException If no subscription with the requested ID is found.
	 */
	void deleteBlockSubscription(String id);

}
