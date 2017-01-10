package com.avatar.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.avatar.model.UserDetails;



@Repository("userDetailsDAO")
public class UserDetailsDAOImpl implements UserDetailsDAO{

	
	 private static final Logger log = LoggerFactory.getLogger(UserDetailsDAOImpl.class);
		 
		@Autowired(required=true)
		private SessionFactory sessionFactory;


		public UserDetailsDAOImpl(SessionFactory sessionFactory) {
			try {
				this.sessionFactory = sessionFactory;
			} catch (Exception e) {
				log.error(" Unable to connect to db" );
				e.printStackTrace();
			}
		}

		@Transactional
		public List<UserDetails> list() {
			log.debug(" Calling list method");
			@SuppressWarnings("unchecked")
			List<UserDetails> list = (List<UserDetails>) sessionFactory.getCurrentSession()
					.createCriteria(UserDetails.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

			return list;
		}

		@Transactional
		public boolean update(UserDetails user) {
			log.debug(" Calling update method");
			try {
				sessionFactory.getCurrentSession().save(user);
				return true;
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
		}
		
		@Transactional
		public boolean save(UserDetails userDetails) {
			log.debug(" Calling save method");
			try {
				sessionFactory.getCurrentSession().update(userDetails);
				
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
		}
		
	


		@Transactional
		public void delete(String id) {
			log.debug(" Calling delete method");
			UserDetails user = new UserDetails();
			user.setId(id);
			sessionFactory.getCurrentSession().delete(user);
		}

		@Transactional
		public UserDetails get(String id, String password) {
			log.debug(" Calling get method");
			String hql = "from UserDetails where id=" + "'"+ id+"'  and password = "
					+"'" + password + "'";
		   return getUser(hql);
		}
		
		@Transactional
		public UserDetails get(String id) {
			log.debug(" Calling get method");
			String hql = "from UserDetails where id=" + "'"+ id + "'" ;
			 return getUser(hql);
			
		}
		
		private UserDetails getUser(String hql)
		{
			log.debug(" Calling getUser method");
	        Query query = sessionFactory.getCurrentSession().createQuery(hql);
			
			@SuppressWarnings("unchecked")
			List<UserDetails> list = (List<UserDetails>) query.list();
			
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
			return null;
		}
		
		@Transactional
		public boolean isValidUserDetails(String id, String password) {
			log.debug(" Calling list isValidUserDetails");
			String hql = "from UserDetails where id= '" + id + "' and " + " password ='" + password+"'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			
			@SuppressWarnings("unchecked")
			List<UserDetails> list = (List<UserDetails>) query.list();
			
			if (list != null && !list.isEmpty()) {
				return true;
			}
			
			return false;
		}




}
