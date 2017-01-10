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

import com.avatar.dao.JobDAO;
import com.avatar.model.Job;
import com.avatar.model.UserRole;



@RestController
public class JobController {
	

   @Autowired
   Job job;

	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	UserRole userRole;

	@RequestMapping(value = "/Job/", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> listAllUserDetailss() {
		List<Job> job = jobDAO.list();
		if (job.isEmpty()) {
			return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);
			// return new ResponseEntity<List<Job>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Job>>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/Job/{id}", method = RequestMethod.POST, 
				                          produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Job> getUserDetails(@RequestParam("id") String id) {
		Job Job = jobDAO.get(id);
		if (Job == null) {
			return new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Job>(Job, HttpStatus.OK);
	}

	@RequestMapping(value = "/Job", method = RequestMethod.POST)
	public ResponseEntity<Void> createUserDetails(@RequestBody Job job, 
			                                 UriComponentsBuilder ucBuilder) {

		if (jobDAO.get(job.getId()) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		jobDAO.saveOrUpdate(job);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/Job/{id}").buildAndExpand(job.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/Job/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Job> updateUserDetails(@PathVariable("id") String id, 
			                                   @RequestBody Job job) {

	
		if (jobDAO.get(id) == null) {
			return new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
		}

	
		jobDAO.saveOrUpdate(job);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/Job/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Job> deleteUserDetails(@PathVariable("id") String id) {
		Job Job = jobDAO.get(id);
		if (Job == null) {
			return new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
		}

		jobDAO.delete(id);
		return new ResponseEntity<Job>(HttpStatus.NO_CONTENT);
	}





}
