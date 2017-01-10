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

import com.avatar.model.Blog;

@Repository("blogDAO")
public class BlogDAOImpl implements BlogDAO{

	private static final Logger log = LoggerFactory.getLogger(UserDetailsDAOImpl.class);

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	public BlogDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			log.error(" Unable to connect to db");
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Blog> list() {
		String hql = "from Blog";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Blog> list = (List<Blog>) query.list();

		return list;
	}

	@Transactional
	public boolean saveOrUpdate(Blog blog) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(blog);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}


	@Transactional
	public void delete(String id) {
		Blog blog = new Blog();
		blog.setId(id);
		sessionFactory.getCurrentSession().delete(blog);
	}

	@Transactional
	public Blog get(String id) {
		String hql = "from Blog where id=" + "'" + id + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Blog> list = (List<Blog>) query.list();

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}

}
