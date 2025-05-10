package com.smartit.truckprojobs.repository;

import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.CandidateSave;
import com.smartit.truckprojobs.model.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateSaveRepository extends JpaRepository<CandidateSave, Long> {

    List<CandidateSave> findByUserId(CandidateProfile userAccountId);

    List<CandidateSave> findByJob(JobPostActivity job);

}