package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.RecruiterProfile;
import com.smartit.truckprojobs.model.Users;
import com.smartit.truckprojobs.repository.CandidateProfileRepository;
import com.smartit.truckprojobs.repository.RecruiterProfileRepository;
import com.smartit.truckprojobs.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    public static final int RECRUITER = 1;
    public static final int CANDIDATE = 2;

    private final UsersRepository usersRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, CandidateProfileRepository candidateProfileRepository,
                        RecruiterProfileRepository recruiterProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();

        if (userTypeId == RECRUITER) { // Recruiter
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            // Candidate
            candidateProfileRepository.save(new CandidateProfile(savedUser));
        }

        return savedUser;
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            // Get the current user's username
            return null;
        }

        String username = authentication.getName();
        Users users = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
        long userId = users.getUserId();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
            return recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
        }
        return candidateProfileRepository.findById(userId).orElse(new CandidateProfile());
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String username = authentication.getName();
        return usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
    }

    public Users findByEmail(String currentUsername) {
        return usersRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not " +
                        "found"));
    }
}