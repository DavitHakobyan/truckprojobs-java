package com.smartit.truckprojobs.repository;


import com.smartit.truckprojobs.model.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {
}