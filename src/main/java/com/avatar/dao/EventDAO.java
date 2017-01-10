package com.avatar.dao;

import java.util.List;

import com.avatar.model.Event;





public interface EventDAO {



	public List<Event> list();

	
	public Event get(String id);


	public boolean saveOrUpdate(Event event);

	public void delete(String id);
	



}
