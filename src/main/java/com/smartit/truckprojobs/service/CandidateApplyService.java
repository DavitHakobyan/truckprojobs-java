package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.CandidateApply;
import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.JobPostActivity;
import com.smartit.truckprojobs.repository.CandidateApplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateApplyService {

    private final CandidateApplyRepository candidateApplyRepository;

    public CandidateApplyService(CandidateApplyRepository candidateApplyRepository) {
        this.candidateApplyRepository = candidateApplyRepository;
    }

    public List<CandidateApply> getCandidatesJobs(CandidateProfile userAccountId) {
        return candidateApplyRepository.findByUserId(userAccountId);
    }

    public List<CandidateApply> getJobCandidates(JobPostActivity job) {
        return candidateApplyRepository.findByJob(job);
    }

    public void addNew(CandidateApply candidateApply) {
        candidateApplyRepository.save(candidateApply);
    }
}