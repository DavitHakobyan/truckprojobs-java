package com.smartit.truckprojobs.repository;

import com.smartit.truckprojobs.model.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {
}