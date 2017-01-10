package com.avatar.dao;

import java.util.List;

import com.avatar.model.Blog;

public interface BlogDAO {



	public List<Blog> list();

	
	public Blog get(String id);


	public boolean saveOrUpdate(Blog blog);

	public void delete(String id);
	



}
