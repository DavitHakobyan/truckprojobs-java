package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.Users;
import com.smartit.truckprojobs.repository.CandidateProfileRepository;
import com.smartit.truckprojobs.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UsersRepository usersRepository;

    public CandidateProfileService(CandidateProfileRepository candidateProfileRepository,
                                   UsersRepository usersRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<CandidateProfile> getOne(Long id) {
        return candidateProfileRepository.findById(id);
    }

    public CandidateProfile addNew(CandidateProfile jobSeekerProfile) {
        return candidateProfileRepository.save(jobSeekerProfile);
    }

    public CandidateProfile getCurrentSeekerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String currentUsername = authentication.getName();
        Users users = usersRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return getOne(users.getUserId()).orElse(null);
    }
}