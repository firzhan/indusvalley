package com.indusvalley.oauth.model.json.response;

import com.indusvalley.oauth.utils.Utils;

public class ErrorResponse {

	private Utils.ERROR_CODES errorCodes;
	private String errorDescription;
	private Utils.OPERATION operation;

	public Utils.ERROR_CODES getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(Utils.ERROR_CODES errorCodes) {
		this.errorCodes = errorCodes;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public Utils.OPERATION getOperation() {
		return operation;
	}

	public void setOperation(Utils.OPERATION operation) {
		this.operation = operation;
	}
}
