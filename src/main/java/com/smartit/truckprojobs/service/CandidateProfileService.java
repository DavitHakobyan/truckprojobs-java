package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.repository.CandidateProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;

    public CandidateProfileService(CandidateProfileRepository candidateProfileRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
    }


    public Optional<CandidateProfile> getOne(Long id) {
        return candidateProfileRepository.findById(id);
    }

    public CandidateProfile addNew(CandidateProfile jobSeekerProfile) {
        return candidateProfileRepository.save(jobSeekerProfile);
    }
}