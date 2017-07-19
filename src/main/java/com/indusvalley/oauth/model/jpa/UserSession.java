package com.indusvalley.oauth.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(UserSessionPK.class)
@Table(name = "USER_SESSION_TABLE")
public class UserSession {

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "ACCESS_TOKEN")
	private String accessToken;

	@Column(name = "ACCESS_TOKEN_TYPE")
	private String accessTokenType;

	@Column(name = "SYSTEM_ACCESS_TOKEN")
	private String systemAccessToken;

	@Column(name = "REFRESH_TOKEN")
	private String refreshToken;

	@Column(name = "EXPIRES_IN")
	private String expiresIn;

	@Column(name = "DATE_CREATED")
	private String dateCreated;

	@Column(name = "CRAETED_BY")
	private String createdBy;

	@Column(name = "DATE_MODIFIED")
	private String dateModified;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Id
	@Column(name = "ACCESS_TOKEN_SOURCE")
	private String accessTokenSource;



	public UserSession() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenType() {
		return accessTokenType;
	}

	public void setAccessTokenType(String accessTokenType) {
		this.accessTokenType = accessTokenType;
	}

	public String getSystemAccessToken() {
		return systemAccessToken;
	}

	public void setSystemAccessToken(String systemAccessToken) {
		this.systemAccessToken = systemAccessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getAccessTokenSource() {
		return accessTokenSource;
	}

	public void setAccessTokenSource(String accessTokenSource) {
		this.accessTokenSource = accessTokenSource;
	}
}
