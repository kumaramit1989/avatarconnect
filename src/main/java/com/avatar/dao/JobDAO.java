package com.avatar.dao;

import java.util.List;

import com.avatar.model.Job;



public interface JobDAO {



	public List<Job> list();

	
	public Job get(String id);


	public boolean saveOrUpdate(Job job);

	public void delete(String id);
	



}
