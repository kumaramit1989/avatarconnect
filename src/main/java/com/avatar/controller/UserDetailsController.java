package com.avatar.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.avatar.dao.UserDetailsDAO;
import com.avatar.model.UserDetails;
import com.avatar.model.UserRole;




@RestController
public class UserDetailsController {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(UserDetailsController.class);
	


   @Autowired
   UserDetails userDetails;

	@Autowired
	UserDetailsDAO userDetailsDAO;
	
	@Autowired
	UserRole userRole;

	@RequestMapping(value = "/UserDetails", method = RequestMethod.GET)
	public ResponseEntity<List<UserDetails>> listAllUserDetailss() {
		logger.debug("calling method listAllUserDetailss");
		List<UserDetails> userDetails = userDetailsDAO.list();
		if (userDetails.isEmpty()) {
			logger.debug("No Users are available");
			return new ResponseEntity<List<UserDetails>>(HttpStatus.NO_CONTENT);
			// return new ResponseEntity<List<UserDetails>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<UserDetails>>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/UserDetails/", method = RequestMethod.POST, 
				                          produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetails> getUserDetails(@RequestParam("id") String id,
			 @RequestParam("password") String password) {
		logger.debug("calling method getUserDetails");
		UserDetails UserDetails = userDetailsDAO.get(id,password);
		if (UserDetails == null) {
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDetails>(UserDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/UserDetails", method = RequestMethod.POST)
	public ResponseEntity<Void> createUserDetails(@RequestBody UserDetails userDetails, 
			                                 UriComponentsBuilder ucBuilder) {
		logger.debug("calling method createUserDetails");

		if (userDetailsDAO.get(userDetails.getId()) != null) {
			logger.debug("User exist with the id :" + userDetails.getId());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		userDetailsDAO.save(userDetails);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/UserDetails/{id}").buildAndExpand(userDetails.getId()).toUri());
		logger.debug("Ending method createUserDetails");
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/UserDetails/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserDetails> updateUserDetails(@PathVariable("id") String id, 
			                                   @RequestBody UserDetails userDetails) {
		logger.debug("calling method updateUserDetails");
		if (userDetailsDAO.get(id) == null) {
			logger.debug("User does not exist with the id" + id);
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
		}

	
		userDetailsDAO.update(userDetails);
		logger.debug("ending method updateUserDetails");
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/UserDetails/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UserDetails> deleteUserDetails(@PathVariable("id") String id) {
		logger.debug("calling method deleteUserDetails");
		UserDetails UserDetails = userDetailsDAO.get(id);
		if (UserDetails == null) {
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
		}

		userDetailsDAO.delete(id);
		logger.debug("endign method deleteUserDetails");
		return new ResponseEntity<UserDetails>(HttpStatus.NO_CONTENT);
	}





}
