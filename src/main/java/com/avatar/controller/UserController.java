package com.avatar.controller;
import java.util.List;

import org.springframework.beans.
factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avatar.dao.UserDetailsDAO;
import com.avatar.model.UserDetails;




@RestController
public class UserController {

	
	@Autowired
	private UserDetailsDAO userDetailsDAO;

	
	@GetMapping("/users")
	public List<?> getUsers() {
		return userDetailsDAO.list();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserDetails> getUser(@PathVariable("id") String id) {

		UserDetails userDetails = userDetailsDAO.get(id);
		if (userDetails == null) {
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}

	@PostMapping(value = "/users")
	public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails userDetails) {

		userDetailsDAO.save(userDetails);

		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) {

		if (null == userDetailsDAO.get(id)) {
			return new ResponseEntity<String>("No UserDetails found for ID " + id, HttpStatus.NOT_FOUND);
		}
		userDetailsDAO.delete(id);
		return new ResponseEntity<String>(id, HttpStatus.OK);

	}

	@PutMapping("/users/{id}")
	public ResponseEntity<UserDetails> updateUser(@PathVariable String id, @RequestBody UserDetails userDetails) {

	
		if (null == userDetailsDAO.get(id)) {
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
		}
		userDetailsDAO.save(userDetails);
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}

}
