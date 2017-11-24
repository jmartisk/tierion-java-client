package cz.srayayay.tierion.dataapi;

import java.util.List;
import java.util.Map;

import cz.srayayay.tierion.dataapi.model.DatastoreObject;
import cz.srayayay.tierion.dataapi.model.GetRecordsResponse;
import cz.srayayay.tierion.dataapi.model.RecordObject;

public interface TierionDataApiClient extends AutoCloseable {

    /**
     * Gets a list of all datastores for the user account.
     * @return List of all known datastores.
     */
    List<DatastoreObject> getAllDatastores();

    /**
     * Retrieves an existing datastore denoted by id.
     * @param id ID of an existing datastore to be retrieved.
     */
    DatastoreObject getDatastore(long id);

    /**
     * Creates a new datastore.
     * @param datastore Representation of the datastore configuration.
     *                  Should contain all properties you wish to apply for the datastore.
     * @return The returned datastore will contain the definition which was returned from the server,
     *         that means it will also contain the "id", "key" and "timestamp" properties.
     */
    DatastoreObject createDatastore(DatastoreObject datastore);

    /**
     * Deletes an existing datastore denoted by id.
     * @param id The ID of the datastore to be deleted.
     */
    void deleteDatastore(long id);

    /**
     * Updates an existing datastore denoted by id.
     * @param id The ID of the datastore to be updated.
     * @param newDatastore Representation of the new datastore. It only needs to contain those properties
     *                     which should be updated. Other properties will stay unchanged.
     * @return
     */
    DatastoreObject updateDatastore(long id, DatastoreObject newDatastore);

    /**
     * Retrieves an existing record denoted by id.
     * @param id The ID of the record to be retrieved.
     * @return
     */
    RecordObject getRecord(String id);

    /**
     * Obtains a list of records from the specified datastore.
     * @param datastoreId A unique numeric identifier for the Datastore from which Records are being requested. [Required]
     * @param page The page number of the Record results to view. If not specified, page will default to 1.
     * @param pageSize The number of Records to include in the Record results, between 1 and 10000. If not specified, pageSize will default to 100.
     * @param startDate A timestamp representing the start of the requested date range for the Record results. If not specified, startDate will default to creation date and time of the Datastore.
     * @param endDate A timestamp representing the end of the requested date range for the Record results. If not specified, endDate will default to the current date and time.
     * @return
     */
    GetRecordsResponse getRecords(Long datastoreId, Long page, Long pageSize, Long startDate, Long endDate);

    /**
     * Creates a new record in the specified datastore.
     * @param datastoreId A unique numeric identifier for the Datastore associated with this Record. [Required]
     * @param data Key/value pairs for the Records's data.
     * @return
     */
    RecordObject createRecord(long datastoreId, Map<String, String> data);

    /**
     * Deletes a record denoted by id.
     * @param id The ID of the record to be deleted.
     */
    void deleteRecord(String id);

    // TODO validateReceipt

}
