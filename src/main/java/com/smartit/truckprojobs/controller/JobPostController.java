package com.smartit.truckprojobs.controller;

import com.smartit.truckprojobs.model.JobPost;
import com.smartit.truckprojobs.service.JobPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/jobposts")
public class JobPostController {

    private final JobPostService jobPostService;

    public JobPostController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    @GetMapping
    public List<JobPost> getAllJobPosts() {
        return jobPostService.getAllJobPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPost> getJobPostById(@PathVariable Long id) {
        return jobPostService.getJobPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public JobPost createJobPost(@RequestBody JobPost jobPost) {
        return jobPostService.createOrUpdateJobPost(jobPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPost> updateJobPost(@PathVariable Long id, @RequestBody JobPost jobPost) {
        return jobPostService.getJobPostById(id)
                .map(existingJobPost -> {
                    jobPost.setId(id);
                    return ResponseEntity.ok(jobPostService.createOrUpdateJobPost(jobPost));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPostById(@PathVariable Long id) {
        if (jobPostService.getJobPostById(id).isPresent()) {
            jobPostService.deleteJobPostById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}