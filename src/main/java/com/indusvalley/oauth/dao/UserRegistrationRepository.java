package com.indusvalley.oauth.dao;

import com.indusvalley.oauth.model.jpa.UserInformation;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserRegistrationRepository extends AbstractRepository<UserInformation> {

	public UserRegistrationRepository(EntityManagerFactory emf) {
		super(emf);
	}

	public void createUser(UserInformation userInformation) {
		create(userInformation);
	}

	public void removeUser(UserInformation userInformation) {
		remove(userInformation);
	}

	public UserInformation findUserAccount(String userId) {
		return find(com.indusvalley.oauth.model.jpa.UserInformation.class ,userId);
	}

	public List<UserInformation> findUserAccounts() {
		return findAll(UserInformation.class);
	}
}
