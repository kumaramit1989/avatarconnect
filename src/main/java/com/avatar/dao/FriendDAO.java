package com.avatar.dao;

import java.util.List;

import com.avatar.model.Friend;



public interface FriendDAO {



	public List<Friend> list();

	
	public Friend get(String id);


	public boolean saveOrUpdate(Friend friend);

	public void delete(String userID, String friendID);
	



}
