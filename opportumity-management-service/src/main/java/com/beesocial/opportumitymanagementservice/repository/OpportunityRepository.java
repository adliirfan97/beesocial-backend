package com.beesocial.opportumitymanagementservice.repository;

import com.beesocial.opportumitymanagementservice.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

}
