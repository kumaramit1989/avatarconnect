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

import com.avatar.dao.UserRoleDAO;
import com.avatar.model.UserRole;



@RestController
public class UserRoleController {
	

   @Autowired
   UserRole userDetails;

	@Autowired
	UserRoleDAO userDetailsDAO;
	
	@Autowired
	UserRole userRole;

	@RequestMapping(value = "/UserRole", method = RequestMethod.GET)
	public ResponseEntity<List<UserRole>> listAllUserRoles() {
		List<UserRole> userDetails = userDetailsDAO.list();
		if (userDetails.isEmpty()) {
			return new ResponseEntity<List<UserRole>>(HttpStatus.NO_CONTENT);
			// return new ResponseEntity<List<UserRole>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<UserRole>>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/UserRole/{id}", method = RequestMethod.POST, 
				                          produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserRole> getUserRole(@RequestParam("id") String id) {
		UserRole UserRole = userDetailsDAO.get(id);
		if (UserRole == null) {
			return new ResponseEntity<UserRole>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserRole>(UserRole, HttpStatus.OK);
	}

	@RequestMapping(value = "/UserRole", method = RequestMethod.POST)
	public ResponseEntity<Void> createUserRole(@RequestBody UserRole userDetails, 
			                                 UriComponentsBuilder ucBuilder) {

		if (userDetailsDAO.get(userDetails.getId()) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		userDetailsDAO.saveOrUpdate(userDetails);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/UserRole/{id}").buildAndExpand(userDetails.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/UserRole/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserRole> updateUserRole(@PathVariable("id") String id, 
			                                   @RequestBody UserRole userRole) {

	
		if (userDetailsDAO.get(id) == null) {
			return new ResponseEntity<UserRole>(HttpStatus.NOT_FOUND);
		}

	
		userDetailsDAO.saveOrUpdate(userRole);
		return new ResponseEntity<UserRole>(userRole, HttpStatus.OK);
	}

	@RequestMapping(value = "/UserRole/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UserRole> deleteUserRole(@PathVariable("id") String id, @PathVariable("id") String roleID) {
		if (userDetailsDAO.get(id) == null) {
			return new ResponseEntity<UserRole>(HttpStatus.NOT_FOUND);
		}

		userDetailsDAO.delete(id,roleID);
		return new ResponseEntity<UserRole>(HttpStatus.NO_CONTENT);
	}





}
