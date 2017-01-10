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

import com.avatar.model.Job;


@Repository("jobDAO")
public class JobDAOImpl implements JobDAO{

	private static final Logger log = LoggerFactory.getLogger(UserDetailsDAOImpl.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	public JobDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			log.error(" Unable to connect to db");
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Job> list() {
		String hql = "from Job";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Job> list = (List<Job>) query.list();

		return list;
	}

	@Transactional
	public boolean saveOrUpdate(Job job) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(job);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}


	@Transactional
	public void delete(String id) {
		Job job = new Job();
		job.setId(id);
		sessionFactory.getCurrentSession().delete(job);
	}

	@Transactional
	public Job get(String id) {
		String hql = "from Job where id=" + "'" + id + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<Job> list = (List<Job>) query.list();

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}

}
