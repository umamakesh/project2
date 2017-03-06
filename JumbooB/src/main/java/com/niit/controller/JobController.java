package com.niit.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.JobDAO;
import com.niit.model.Job;


@RestController
public class JobController {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobDAO jobDAO;
	
	
	
	@RequestMapping(value="/postJob",method=RequestMethod.POST)
	public ResponseEntity<?> postJob(@RequestBody Job job,HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null){
			Error error=new Error("Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
		}
		else{
					job.setPostedOn(new Date());
					jobDAO.postJob(job);
				return new ResponseEntity<Void>(HttpStatus.OK);
			
	}
}
	
	@RequestMapping(value="/getAllJobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session){
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		System.out.println("USER is null");
    		Error error=new Error("Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
    	}
    	System.out.println("USER OBJECT " + user.getRoles());
    	List<Job> jobs=jobDAO.getAllJobs();
    	return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
    }
	
	
	@RequestMapping(value="/getJobDetail/{jobId}",method=RequestMethod.GET)
    public ResponseEntity<?> getJobDetail(@PathVariable(value="jobId")int jobId,
    		HttpSession session){
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		System.out.println("USER is null");
    		Error error = new Error("Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
    	}
    	logger.debug("JobId "+ jobId);
    	Job jobDetail=jobDAO.getJobDetail(jobId);
    	return new ResponseEntity<Job>(jobDetail,HttpStatus.OK);
    }
}