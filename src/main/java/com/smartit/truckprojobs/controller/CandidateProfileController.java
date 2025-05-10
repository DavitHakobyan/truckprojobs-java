package com.smartit.truckprojobs.controller;

import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.Skills;
import com.smartit.truckprojobs.model.Users;
import com.smartit.truckprojobs.repository.UsersRepository;
import com.smartit.truckprojobs.service.CandidateProfileService;
import com.smartit.truckprojobs.util.FileDownloadUtil;
import com.smartit.truckprojobs.util.FileUploadUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;
    private final UsersRepository usersRepository;

    public CandidateProfileController(CandidateProfileService candidateProfileService,
                                      UsersRepository usersRepository) {
        this.candidateProfileService = candidateProfileService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String jobSeekerProfile(Model model) {
        CandidateProfile candidateProfile = new CandidateProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            Optional<CandidateProfile> seekerProfile = candidateProfileService.getOne(user.getUserId());
            if (seekerProfile.isPresent()) {
                candidateProfile = seekerProfile.get();
                if (candidateProfile.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    candidateProfile.setSkills(skills);
                }
            }

            model.addAttribute("skills", skills);
            model.addAttribute("profile", candidateProfile);
        }

        return "job-seeker-profile";
    }

    @PostMapping("/addNew")
    public String addNew(CandidateProfile candidateProfile,
                         @RequestParam("image") MultipartFile image,
                         @RequestParam("pdf") MultipartFile pdf,
                         Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            candidateProfile.setUserId(user);
            candidateProfile.setUserAccountId(user.getUserId());
        }

        List<Skills> skillsList = new ArrayList<>();
        model.addAttribute("profile", candidateProfile);
        model.addAttribute("skills", skillsList);

        for (Skills skills : candidateProfile.getSkills()) {
            skills.setCandidateProfile(candidateProfile);
        }

        String imageName = "";
        String resumeName = "";

        if (!Objects.equals(image.getOriginalFilename(), "")) {
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            candidateProfile.setProfilePhoto(imageName);
        }

        if (!Objects.equals(pdf.getOriginalFilename(), "")) {
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            candidateProfile.setResume(resumeName);
        }

        CandidateProfile seekerProfile = candidateProfileService.addNew(candidateProfile);

        try {
            String uploadDir = "photos/candidate/" + candidateProfile.getUserAccountId();
            if (!Objects.equals(image.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, imageName, image);
            }
            if (!Objects.equals(pdf.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, resumeName, pdf);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return "redirect:/dashboard/";
    }

    @GetMapping("/{id}")
    public String candidateProfile(@PathVariable("id") long id, Model model) {
        Optional<CandidateProfile> seekerProfile = candidateProfileService.getOne(id);
        model.addAttribute("profile", seekerProfile.get());
        return "job-seeker-profile";
    }

    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName, @RequestParam(value = "userID") String userId) {

        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        Resource resource = null;

        try {
            resource = downloadUtil.getFileAsResourse("photos/candidate/" + userId, fileName);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }
}










