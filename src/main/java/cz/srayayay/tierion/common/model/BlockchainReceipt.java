package cz.srayayay.tierion.common.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srayayay.tierion.common.JsonHelper;

public class BlockchainReceipt {

	@JsonProperty("@context")
	private String context;

	@JsonProperty
	private String type;

	@JsonProperty
	private String targetHash;

	@JsonProperty
	private String merkleRoot;

	@JsonProperty
	private List<Proof> proof;

	@JsonProperty
	private List<Anchor> anchors;

	public String getContext() {
		return context;
	}

	public String getType() {
		return type;
	}

	public String getTargetHash() {
		return targetHash;
	}

	public String getMerkleRoot() {
		return merkleRoot;
	}

	public List<Proof> getProof() {
		return proof;
	}

	public List<Anchor> getAnchors() {
		return anchors;
	}

	public static class Anchor {

		@JsonProperty
		private String type;

		@JsonProperty
		private String sourceId;

		public String getType() {
			return type;
		}

		public String getSourceId() {
			return sourceId;
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Proof {

		@JsonProperty
		private String left;

		@JsonProperty
		private String right;

		public String getLeft() {
			return left;
		}

		public String getRight() {
			return right;
		}
	}

	@Override
	public String toString() {
		return JsonHelper.toJson(this);
	}

}
