package com.beesocial.eventmanagementservice.repository;

import com.beesocial.eventmanagementservice.model.EventApplicant;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventApplicantRepository extends JpaRepository<EventApplicant, Integer> {
}
