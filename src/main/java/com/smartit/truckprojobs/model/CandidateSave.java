package com.smartit.truckprojobs.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "candidate_save", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "job"})})
public class CandidateSave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_account_id")
    private CandidateProfile userId;

    @Setter
    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "jobPostId")
    private JobPostActivity job;

    public CandidateSave() {
    }

    public CandidateSave(Long id, CandidateProfile userId, JobPostActivity job) {
        this.id = id;
        this.userId = userId;
        this.job = job;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CandidateSave{" +
                "id=" + id +
                ", userId=" + userId.toString() +
                ", job=" + job.toString() +
                '}';
    }
}
