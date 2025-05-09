package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.JobPost;
import com.smartit.truckprojobs.repository.JobPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }

    public Optional<JobPost> getJobPostById(Long id) {
        return jobPostRepository.findById(id);
    }

    public JobPost createOrUpdateJobPost(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    public void deleteJobPostById(Long id) {
        jobPostRepository.deleteById(id);
    }
}