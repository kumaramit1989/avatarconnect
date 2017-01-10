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

import com.avatar.dao.EventDAO;
import com.avatar.model.Event;
import com.avatar.model.UserRole;



@RestController
public class EventController {
	

   @Autowired
   Event event;

	@Autowired
	EventDAO eventDAO;
	
	@Autowired
	UserRole userRole;

	@RequestMapping(value = "/Event/", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> listAllUserDetailss() {
		List<Event> event = eventDAO.list();
		if (event.isEmpty()) {
			return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
			// return new ResponseEntity<List<Event>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Event>>(event, HttpStatus.OK);
	}

	@RequestMapping(value = "/Event/{id}", method = RequestMethod.POST, 
				                          produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> getUserDetails(@RequestParam("id") String id) {
		Event Event = eventDAO.get(id);
		if (Event == null) {
			return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Event>(Event, HttpStatus.OK);
	}

	@RequestMapping(value = "/Event", method = RequestMethod.POST)
	public ResponseEntity<Void> createUserDetails(@RequestBody Event event, 
			                                 UriComponentsBuilder ucBuilder) {

		if (eventDAO.get(event.getId()) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		eventDAO.saveOrUpdate(event);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/Event/{id}").buildAndExpand(event.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/Event/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Event> updateUserDetails(@PathVariable("id") String id, 
			                                   @RequestBody Event Event) {

		
		if (eventDAO.get(id) == null) {
			return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
		}

	
		eventDAO.saveOrUpdate(Event);
		return new ResponseEntity<Event>(Event, HttpStatus.OK);
	}

	@RequestMapping(value = "/Event/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Event> deleteUserDetails(@PathVariable("id") String id) {
		Event Event = eventDAO.get(id);
		if (Event == null) {
			return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
		}

		eventDAO.delete(id);
		return new ResponseEntity<Event>(HttpStatus.NO_CONTENT);
	}





}
