package com.niit.dao;



import java.util.List;

import com.niit.model.Job;

public interface JobDAO {
	
	void postJob(Job job);
	List<Job> getAllJobs();
	Job getJobDetail(int jobId);

}

