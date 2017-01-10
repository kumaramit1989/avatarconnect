package com.avatar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.avatar.model.UserDetails;



@Repository("userDetailsDAO")
public interface UserDetailsDAO {



	public List<UserDetails> list();

	public UserDetails get(String id, String password);
	
	public UserDetails get(String id);


	public boolean save(UserDetails userDetailsDetails);
	
	public boolean update(UserDetails userDetailsDetails);

	public void delete(String id);
	
	public boolean isValidUserDetails(String id, String name);




}
