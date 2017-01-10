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

import com.avatar.model.Event;




@Repository("eventDAO")
public class EventDAOImpl implements EventDAO{

	private static final Logger log = LoggerFactory.getLogger(UserDetailsDAOImpl.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	public EventDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			log.error(" Unable to connect to db");
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Event> list() {
		String hql = "from Event";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Event> list = (List<Event>) query.list();

		return list;
	}

	@Transactional
	public boolean saveOrUpdate(Event event) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(event);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}


	@Transactional
	public void delete(String id) {
		
		Event event = new Event();
		event.setId(id);
		sessionFactory.getCurrentSession().delete(event);
	}

	@Transactional
	public Event get(String id) {
		String hql = "from Event where id=" + "'" + id + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<Event> list = (List<Event>) query.list();

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}

}
