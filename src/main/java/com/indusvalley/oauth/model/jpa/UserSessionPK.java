package com.indusvalley.oauth.model.jpa;

import java.io.Serializable;

public class UserSessionPK implements CompositePKInterface {

	private String userId = null;
	private String accessTokenSource;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessTokenSource() {
		return accessTokenSource;
	}

	public void setAccessTokenSource(String accessTokenSource) {
		this.accessTokenSource = accessTokenSource;
	}
}
