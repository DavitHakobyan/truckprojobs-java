package com.smartit.truckprojobs.repository;

import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.CandidateSave;
import com.smartit.truckprojobs.model.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateSaveRepository extends JpaRepository<CandidateSave, Long> {

    List<CandidateSave> findByUserId(CandidateProfile userAccountId);

    List<CandidateSave> findByJob(JobPostActivity job);

}