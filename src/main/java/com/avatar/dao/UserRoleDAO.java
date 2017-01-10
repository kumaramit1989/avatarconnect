package com.avatar.dao;

import java.util.List;

import com.avatar.model.UserRole;



public interface UserRoleDAO {

	public List<UserRole> list();
	
	public UserRole get(String id);

	public boolean saveOrUpdate(UserRole userRole);

	public void delete(String userID, String roleID);

}
