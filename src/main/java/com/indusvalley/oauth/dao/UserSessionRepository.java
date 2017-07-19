package com.indusvalley.oauth.dao;

import com.indusvalley.oauth.model.jpa.CompositePKInterface;
import com.indusvalley.oauth.model.jpa.UserSession;
import com.indusvalley.oauth.model.jpa.UserSessionPK;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserSessionRepository extends AbstractRepository<UserSession> {

	public UserSessionRepository(EntityManagerFactory emf) {
		super(emf);
	}

	public void createUser(UserSession userSession) {
		create(userSession);
	}

	public void removeUser(UserSession userSession) {
		remove(userSession);
	}

	public UserSession findUserAccount(CompositePKInterface compositePKInterface) {
		return findFromCompositeKey(com.indusvalley.oauth.model.jpa.UserSession.class, compositePKInterface);
	}

	public List<UserSession> findUserAccounts() {
		return findAll(UserSession.class);
	}
}
