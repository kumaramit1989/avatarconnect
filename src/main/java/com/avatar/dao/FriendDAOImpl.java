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

import com.avatar.model.Friend;


@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO{

	private static final Logger log = LoggerFactory.getLogger(UserDetailsDAOImpl.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			log.error(" Unable to connect to db");
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Friend> list() {
		String hql = "from Friend";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Friend> list = (List<Friend>) query.list();

		return list;
	}

	@Transactional
	public boolean saveOrUpdate(Friend friend) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(friend);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}


	@Transactional
	public void delete(String userID, String friendID) {
		Friend friend = new Friend();
		friend.setFriendID(friendID);
		friend.setUserID(userID);
		sessionFactory.getCurrentSession().delete(friend);
	}

	@Transactional
	public Friend get(String id) {
		String hql = "from Friend where id=" + "'" + id + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		List<Friend> list = (List<Friend>) query.list();

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}

}
