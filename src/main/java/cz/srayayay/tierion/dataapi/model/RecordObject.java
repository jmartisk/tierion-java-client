package cz.srayayay.tierion.dataapi.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srayayay.tierion.common.JsonHelper;
import cz.srayayay.tierion.common.model.BlockchainReceipt;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("insights")  // TODO insights not supported yet
public class RecordObject {

    @JsonProperty
    private String id;

    @JsonProperty
    private String label;

    @JsonProperty
    private Long accountId;

    @JsonProperty
    private Long datastoreId;

    @JsonProperty
    private RecordStatus status;

    @JsonProperty
    private String json;

    @JsonProperty
    private Map<String, String> data;

    @JsonProperty
    private String sha256;

    @JsonProperty
    private Long timestamp;

    @JsonProperty("blockchain_receipt")
    private BlockchainReceipt blockchainReceipt;

    public String getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getDatastoreId() {
        return datastoreId;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public String getJson() {
        return json;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getSha256() {
        return sha256;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public BlockchainReceipt getBlockchainReceipt() {
        return blockchainReceipt;
    }

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

}
