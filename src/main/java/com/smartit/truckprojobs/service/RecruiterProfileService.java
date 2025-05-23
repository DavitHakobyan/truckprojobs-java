package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.RecruiterProfile;
import com.smartit.truckprojobs.model.Users;
import com.smartit.truckprojobs.repository.RecruiterProfileRepository;
import com.smartit.truckprojobs.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterRepository;
    private final UsersRepository usersRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterRepository,
                                   UsersRepository usersRepository) {
        this.recruiterRepository = recruiterRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<RecruiterProfile> getOne(Long id) {
        return recruiterRepository.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterRepository.save(recruiterProfile);
    }

    public RecruiterProfile getCurrentRecruiterProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String currentUsername = authentication.getName();
        Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Optional<RecruiterProfile> recruiterProfile = getOne(users.getUserId());
        return recruiterProfile.orElse(null);
    }
}