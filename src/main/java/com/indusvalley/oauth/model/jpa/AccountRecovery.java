package com.indusvalley.oauth.model.jpa;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "ACCOUNT_RECOVERY_TABLE")
public class AccountRecovery {

	@Id
	@Column(name = "USER_ID")
	private String userID;

	@Column(name = "ACCOUNT_RECOVERY_TOKEN")
	private String token;

	@Column(name = "DATE_CREATED")
	private String dateCreated;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Account_Recovery{" +
				"id=" + userID +
				", token='" + token + '\'' +
				'}';
	}
}
