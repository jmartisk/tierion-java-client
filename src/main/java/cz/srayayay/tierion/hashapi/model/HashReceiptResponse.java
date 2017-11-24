package cz.srayayay.tierion.hashapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HashReceiptResponse {

	@JsonProperty
	private String receipt;

	public String getReceipt() {
		return receipt;
	}
}
