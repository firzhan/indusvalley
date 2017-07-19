package com.indusvalley.oauth.dao;

import com.indusvalley.oauth.model.jpa.UserLoginInformation;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserLoginRepository  extends AbstractRepository<UserLoginInformation> {
	public UserLoginRepository(EntityManagerFactory emf) {
		super(emf);
	}

	public void createUser(UserLoginInformation userLoginInformation) {
		create(userLoginInformation);
	}

	public void removeUser(UserLoginInformation userLoginInformation) {
		remove(userLoginInformation);
	}

	public UserLoginInformation findUserAccount(String userId) {
		return find(com.indusvalley.oauth.model.jpa
				.UserLoginInformation.class, userId);
	}

	public List<UserLoginInformation> findUserAccounts() {
		return findAll(UserLoginInformation.class);
	}
}
