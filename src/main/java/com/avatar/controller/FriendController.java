package com.avatar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.avatar.dao.FriendDAO;
import com.avatar.model.Friend;
import com.avatar.model.UserRole;


@RestController
public class FriendController {
	

   @Autowired
   Friend friend;

	@Autowired
	FriendDAO friendDAO;
	
	@Autowired
	UserRole userRole;

	@RequestMapping(value = "/Friend/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> listAllUserDetailss() {
		List<Friend> friend = friendDAO.list();
		if (friend.isEmpty()) {
			return new ResponseEntity<List<Friend>>(HttpStatus.NO_CONTENT);
			// return new ResponseEntity<List<Friend>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Friend>>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/Friend/{id}", method = RequestMethod.POST, 
				                          produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Friend> getUserDetails(@RequestParam("id") String id) {
		Friend Friend = friendDAO.get(id);
		if (Friend == null) {
			return new ResponseEntity<Friend>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Friend>(Friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/Friend", method = RequestMethod.POST)
	public ResponseEntity<Void> createUserDetails(@RequestBody Friend friend, 
			                                 UriComponentsBuilder ucBuilder) {

		if (friendDAO.get(friend.getUserID()) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		friendDAO.saveOrUpdate(friend);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/Friend/{id}").buildAndExpand(friend.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/Friend/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> updateUserDetails(@PathVariable("id") String id, 
			                                   @RequestBody Friend friend) {

	

		if (friendDAO.get(id) == null) {
			return new ResponseEntity<Friend>(HttpStatus.NOT_FOUND);
		}

		friendDAO.saveOrUpdate(friend);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/Friend/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Friend> deleteUserDetails(@PathVariable("id") String id,@PathVariable("friendID") String friendID) {
		Friend Friend = friendDAO.get(id);
		if (Friend == null) {
			return new ResponseEntity<Friend>(HttpStatus.NOT_FOUND);
		}

		friendDAO.delete(id,friendID);
		return new ResponseEntity<Friend>(HttpStatus.NO_CONTENT);
	}





}
