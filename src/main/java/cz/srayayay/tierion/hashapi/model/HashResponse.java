package cz.srayayay.tierion.hashapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HashResponse {

	@JsonProperty
	private String receiptId;

	@JsonProperty
	private Long timestamp;

	public String getReceiptId() {
		return receiptId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

}
