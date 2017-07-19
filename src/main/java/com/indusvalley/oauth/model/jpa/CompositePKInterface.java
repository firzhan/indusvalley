package com.indusvalley.oauth.model.jpa;

import java.io.Serializable;

public interface CompositePKInterface extends Serializable {

	public String getUserId();
	public void setUserId(String userId);
	public String getAccessTokenSource();
	public void setAccessTokenSource(String accessTokenSource);


}
