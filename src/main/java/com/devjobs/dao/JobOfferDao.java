package com.devjobs.dao;

import com.devjobs.domain.JobOffer;
import java.util.List;
import java.util.Optional;

public interface JobOfferDao {
    Long create(JobOffer o);

    Optional<JobOffer> findById(Long id);

    List<JobOffer> findAll(int page, int pageSize);

    boolean update(JobOffer o);

    boolean delete(Long id);
}