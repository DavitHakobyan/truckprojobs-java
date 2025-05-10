package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.CandidateSave;
import com.smartit.truckprojobs.model.JobPostActivity;
import com.smartit.truckprojobs.repository.CandidateSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateSaveService {

    private final CandidateSaveRepository candidateSaveRepository;

    public CandidateSaveService(CandidateSaveRepository candidateSaveRepository) {
        this.candidateSaveRepository = candidateSaveRepository;
    }

    public List<CandidateSave> getCandidatesJob(CandidateProfile userAccountId) {
        return candidateSaveRepository.findByUserId(userAccountId);
    }

    public List<CandidateSave> getJobCandidates(JobPostActivity job) {
        return candidateSaveRepository.findByJob(job);
    }

    public void addNew(CandidateSave jobSeekerSave) {
        candidateSaveRepository.save(jobSeekerSave);
    }
}