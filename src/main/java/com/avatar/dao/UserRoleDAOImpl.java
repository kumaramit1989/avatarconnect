package com.avatar.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.avatar.model.UserRole;



@Repository("userRoleDAO")
public class UserRoleDAOImpl implements UserRoleDAO{

	private static final Logger log = LoggerFactory.getLogger(UserDetailsDAOImpl.class);
	
	@Autowired
	UserRole userRole;
	
	@Autowired
	UserDetailsDAO userDetailsDAO;
	
		
	@Autowired(required=true)
	RoleDAO roleDAO;
	
	@Autowired(required = true)
	private SessionFactory sessionFactory;

	public UserRoleDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			log.error(" Unable to connect to db");
			e.printStackTrace();
		}
	}

	@Transactional
	public List<UserRole> list() {
		String hql = "from UserRole";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<UserRole> list = (List<UserRole>) query.list();

		return list;
	}

	@Transactional
	public boolean saveOrUpdate(UserRole userRole) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(userRole);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}


	@Transactional
	public void delete(String userID, String roleID) {
		UserRole userRole = new UserRole();
		userRole.setRole(roleDAO.get(userID));
		userRole.setUserDetails(userDetailsDAO.get(userID));
		sessionFactory.getCurrentSession().delete(userRole);
	}

	@Transactional
	public UserRole get(String id) {
		String hql = "from UserRole where id=" + "'" + id+ "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<UserRole> list = (List<UserRole>) query.list();

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}

}
