package com.beesocial.opportunitymanagementservice.repository;

import com.beesocial.opportunitymanagementservice.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

}
