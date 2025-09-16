package com.devjobs.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JobOffer {
    private Long id;
    private String title;
    private String description;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String location;
    private LocalDateTime createdAt;
    private Long companyId;

    public JobOffer() {
    }

    public JobOffer(Long id, String title, String description,
            BigDecimal minSalary, BigDecimal maxSalary,
            String location, LocalDateTime createdAt, Long companyId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
        this.createdAt = createdAt;
        this.companyId = companyId;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "JobOffer{id=%d, title='%s', location='%s', companyId=%d}"
                .formatted(id, title, location, companyId);
    }
}