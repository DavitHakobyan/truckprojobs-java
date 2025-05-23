package com.smartit.truckprojobs.controller;

import com.smartit.truckprojobs.model.*;
import com.smartit.truckprojobs.service.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CandidateApplyController {

    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;
    private final CandidateApplyService candidateApplyService;
    private final CandidateSaveService candidateSaveService;
    private final RecruiterProfileService recruiterProfileService;
    private final CandidateProfileService candidateProfileService;

    public CandidateApplyController(JobPostActivityService jobPostActivityService,
                                    UsersService usersService,
                                    CandidateApplyService candidateApplyService,
                                    CandidateSaveService candidateSaveService,
                                    RecruiterProfileService recruiterProfileService,
                                    CandidateProfileService candidateProfileService) {
        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;
        this.candidateApplyService = candidateApplyService;
        this.candidateSaveService = candidateSaveService;
        this.recruiterProfileService = recruiterProfileService;
        this.candidateProfileService = candidateProfileService;
    }

    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model) {
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        List<CandidateApply> jobSeekerApplyList = candidateApplyService.getJobCandidates(jobDetails);
        List<CandidateSave> jobSeekerSaveList = candidateSaveService.getJobCandidates(jobDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
                if (user != null) {
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            } else {
                CandidateProfile user = candidateProfileService.getCurrentSeekerProfile();
                if (user != null) {
                    boolean exists = false;
                    boolean saved = false;
                    for (CandidateApply jobSeekerApply : jobSeekerApplyList) {
                        if (Objects.equals(jobSeekerApply.getUserId().getUserAccountId(), user.getUserAccountId())) {
                            exists = true;
                            break;
                        }
                    }
                    for (CandidateSave jobSeekerSave : jobSeekerSaveList) {
                        if (Objects.equals(jobSeekerSave.getUserId().getUserAccountId(), user.getUserAccountId())) {
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }

        CandidateApply jobSeekerApply = new CandidateApply();
        model.addAttribute("applyJob", jobSeekerApply);

        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "job-details";
    }

    @PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id") int id, CandidateApply candidateApply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<CandidateProfile> seekerProfile = candidateProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
            if (seekerProfile.isPresent() && jobPostActivity != null) {
                candidateApply = new CandidateApply();
                candidateApply.setUserId(seekerProfile.get());
                candidateApply.setJob(jobPostActivity);
                candidateApply.setApplyDate(new Date());
            } else {
                throw new RuntimeException("User not found");
            }

            candidateApplyService.addNew(candidateApply);
        }

        return "redirect:/dashboard/";
    }
}