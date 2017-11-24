package cz.srayayay.tierion.dataapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srayayay.tierion.common.JsonHelper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetRecordsResponse {

    /**
     * "accountId": 1,
     "datastoreId": 95,
     "page": 10,
     "pageCount": 13,
     "pageSize": 5,
     "recordCount": 64,
     "startDate": 1430955005,
     "endDate": 1444559468,
     */

    @JsonProperty
    private Long accountId;

    @JsonProperty
    private Long datastoreId;

    @JsonProperty
    private Long page;

    @JsonProperty
    private Long pageCount;

    @JsonProperty
    private Long pageSize;

    @JsonProperty
    private Long recordCount;

    @JsonProperty
    private Long startDate;

    @JsonProperty
    private Long endDate;

    @JsonProperty
    private List<RecordObject> records;

    public Long getAccountId() {
        return accountId;
    }

    public Long getDatastoreId() {
        return datastoreId;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public Long getStartDate() {
        return startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public List<RecordObject> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }
}
