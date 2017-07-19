package com.indusvalley.oauth.model.json;

public class ResponseStatus {

	private String status;
	private String operation;

	public ResponseStatus() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}
