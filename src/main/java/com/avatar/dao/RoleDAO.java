package com.avatar.dao;
import java.util.List;

import com.avatar.model.Role;



public interface RoleDAO {



	public List<Role> list();

	
	public Role get(String id);


	public boolean saveOrUpdate(Role role);

	public void delete(String id);
	



}
