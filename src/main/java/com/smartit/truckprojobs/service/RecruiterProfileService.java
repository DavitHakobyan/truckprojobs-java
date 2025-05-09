package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.RecruiterProfile;
import com.smartit.truckprojobs.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    public Optional<RecruiterProfile> getOne(Long id) {
        return recruiterRepository.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterRepository.save(recruiterProfile);
    }
}