package com.smartit.truckprojobs.model;

public interface IRecruiterJobs {

    Long getTotalCandidates();

    long getJob_post_id();

    String getJob_title();

    long getLocationId();

    String getCity();

    String getState();

    String getCountry();

    long getCompanyId();

    String getName();
}