package com.indusvalley.oauth.model.json;

public class UserIdAndPasswordRequest {

	private String userPassword;

	private String userId;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public UserIdAndPasswordRequest() {
	}
}
