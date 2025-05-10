package com.smartit.truckprojobs.repository;

import com.smartit.truckprojobs.model.CandidateApply;
import com.smartit.truckprojobs.model.CandidateProfile;
import com.smartit.truckprojobs.model.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateApplyRepository extends JpaRepository<CandidateApply, Long> {

    List<CandidateApply> findByUserId(CandidateProfile userId);

    List<CandidateApply> findByJob(JobPostActivity job);
}