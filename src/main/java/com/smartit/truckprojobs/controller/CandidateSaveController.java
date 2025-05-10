package com.smartit.truckprojobs.controller;

import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.CandidateSave;
import com.smartit.truckprojobs.model.JobPostActivity;
import com.smartit.truckprojobs.model.Users;
import com.smartit.truckprojobs.service.CandidateProfileService;
import com.smartit.truckprojobs.service.CandidateSaveService;
import com.smartit.truckprojobs.service.JobPostActivityService;
import com.smartit.truckprojobs.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class CandidateSaveController {

    private final UsersService usersService;
    private final CandidateProfileService candidateProfileService;
    private final JobPostActivityService jobPostActivityService;
    private final CandidateSaveService candidateSaveService;

    public CandidateSaveController(UsersService usersService,
                                   CandidateProfileService candidateProfileService,
                                   JobPostActivityService jobPostActivityService,
                                   CandidateSaveService candidateSaveService) {
        this.usersService = usersService;
        this.candidateProfileService = candidateProfileService;
        this.jobPostActivityService = jobPostActivityService;
        this.candidateSaveService = candidateSaveService;
    }

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") int id, CandidateSave candidateSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<CandidateProfile> seekerProfile = candidateProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
            if (seekerProfile.isPresent() && jobPostActivity != null) {
                candidateSave.setJob(jobPostActivity);
                candidateSave.setUserId(seekerProfile.get());
            } else {
                throw new RuntimeException("User not found");
            }
            candidateSaveService.addNew(candidateSave);
        }
        return "redirect:/dashboard/";
    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model) {

        List<JobPostActivity> jobPost = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<CandidateSave> candidateSaveList = candidateSaveService.getCandidatesJob((CandidateProfile) currentUserProfile);
        for (CandidateSave candidateSave : candidateSaveList) {
            jobPost.add(candidateSave.getJob());
        }

        model.addAttribute("jobPost", jobPost);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";
    }
}