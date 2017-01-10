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

import com.avatar.dao.RoleDAO;
import com.avatar.model.Role;
import com.avatar.model.UserRole;



@RestController
public class RoleController {
	

   @Autowired
   Role role;

	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	UserRole userRole;

	@RequestMapping(value = "/Role", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> listAllRoles() {
		List<Role> role = roleDAO.list();
		if (role.isEmpty()) {
			return new ResponseEntity<List<Role>>(HttpStatus.NO_CONTENT);
			// return new ResponseEntity<List<Role>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Role>>(role, HttpStatus.OK);
	}

	@RequestMapping(value = "/Role/{id}", method = RequestMethod.POST, 
				                          produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> getRole(@RequestParam("id") String id) {
		Role Role = roleDAO.get(id);
		if (Role == null) {
			return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Role>(Role, HttpStatus.OK);
	}

	@RequestMapping(value = "/Role", method = RequestMethod.POST)
	public ResponseEntity<Void> createRole(@RequestBody Role role, 
			                                 UriComponentsBuilder ucBuilder) {

		if (roleDAO.get(role.getId()) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		roleDAO.saveOrUpdate(role);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/Role/{id}").buildAndExpand(role.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/Role/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Role> updateRole(@PathVariable("id") String id, 
			                                   @RequestBody Role role) {

	
		if (roleDAO.get(id) == null) {
			return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
		}
	roleDAO.saveOrUpdate(role);
		return new ResponseEntity<Role>(role, HttpStatus.OK);
	}

	@RequestMapping(value = "/Role/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Role> deleteRole(@PathVariable("id") String id) {
		Role Role = roleDAO.get(id);
		if (Role == null) {
			return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
		}

		roleDAO.delete(id);
		return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
	}





}
