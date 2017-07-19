package com.indusvalley.oauth.dao;

import com.indusvalley.oauth.model.jpa.AccountRecovery;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AccountRecoveryRepository extends AbstractRepository<AccountRecovery>  {

	public AccountRecoveryRepository(EntityManagerFactory emf) {
		super(emf);
	}

	public void createUser(AccountRecovery accountRecovery) {
		create(accountRecovery);
	}

	public void removeUser(AccountRecovery accountRecovery) {
		remove(accountRecovery);
	}

	public AccountRecovery findUserAccount(String userId) {
		return find(com.indusvalley.oauth.model.jpa.AccountRecovery.class, userId);
	}

	public List<AccountRecovery> findUserAccounts() {
		return findAll(AccountRecovery.class);
	}
}
