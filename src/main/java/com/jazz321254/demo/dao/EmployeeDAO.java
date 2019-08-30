package com.jazz321254.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.jazz321254.demo.model.Employees;

@Repository
@Transactional
public class EmployeeDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 
	 * use HQL to query
	 * 
	 * @param email
	 * @return
	 */
	public List<Employees> findByCondition(String email) {
		LOGGER.debug("getting Employee list by email: {}", email);

		String hql = "FROM Employees WHERE 1 = 1 ";
		if (!ObjectUtils.isEmpty(email)) {
			hql += "AND email = :email ";
		}
		try {
			TypedQuery<Employees> query = entityManager.createQuery(hql, Employees.class);
			if (!ObjectUtils.isEmpty(email)) {
				query.setParameter("email", email);
			}

			List<Employees> list = query.getResultList();
			LOGGER.debug("get B2bTourOrderPayment list successful");
			return list;
		} catch (RuntimeException re) {
			LOGGER.error("get failed: {}", re);
			throw re;
		}
	}

	/**
	 * 
	 * use native query to search
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Employees> findList() {
		LOGGER.debug("getting Employee list ");

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM EMPLOYEES e, DEPARTMENT d WHERE e.DEP_ID = d.DEP_ID");
		try {
			Query query = entityManager.createNativeQuery(sql.toString(), Employees.class);
			List<Employees> list = query.getResultList();
			LOGGER.debug("get Employees list successful");
			return list;
		} catch (RuntimeException re) {
			LOGGER.error("get failed: {}", re);
			throw re;
		}
	}

}
