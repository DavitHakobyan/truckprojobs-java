package com.smartit.truckprojobs.service;

import com.smartit.truckprojobs.model.Candidate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    public List<Candidate> getAllCandidates() {
        return List.of(new Candidate(1L, "Davit Hakobyan")); // Placeholder return statement
    }
}
