package com.indusvalley.oauth.dao;

import com.indusvalley.oauth.model.jpa.CompositePKInterface;
import com.indusvalley.oauth.model.jpa.UserSessionPK;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AbstractRepository<T> {

	private EntityManagerFactory emf;

	public AbstractRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}

	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}


	protected void create(T t) {
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		manager.close();
	}

	protected void remove(T t) {
		EntityManager manager = getEntityManager();
		//em.remove(em.contains(entity) ? entity : em.merge(entity));
		manager.getTransaction().begin();
		manager.remove(manager.contains(t)?t: manager.merge(t));
		//manager.remove(t);
		manager.getTransaction().commit();
		manager.close();
	}

	protected T find(Class<T> tClass, String userId) {
		return getEntityManager().find(tClass, userId);
	}
	protected T findFromCompositeKey(Class<T> tClass, CompositePKInterface compositePKInterface) {
		return getEntityManager().find(tClass, compositePKInterface);
	}

	/*protected com.indusvalley.oauth.model.jpa.AccountRecovery find(String userId) {
		return getEntityManager().find(com.indusvalley.oauth.model.jpa.AccountRecovery.class, userId);
	}

	protected com.indusvalley.oauth.model.jpa.UserInformation find2(String userId) {
		return getEntityManager().find(com.indusvalley.oauth.model.jpa.UserInformation.class, userId);
	}

	protected com.indusvalley.oauth.model.jpa.UserLoginInformation find3(String userId) {
		return getEntityManager().find(com.indusvalley.oauth.model.jpa.UserLoginInformation.class, userId);
	}

	protected com.indusvalley.oauth.model.jpa.UserSession find4(String userId) {
		return getEntityManager().find(com.indusvalley.oauth.model.jpa.UserSession.class, userId);
	}*/


	protected List<T> findAll(Class<T> clazz) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> rootEntry = cq.from(clazz);
		CriteriaQuery<T> all = cq.select(rootEntry);
		TypedQuery<T> allQuery = getEntityManager().createQuery(all);
		return allQuery.getResultList();
	}
}
