package com.beesocial.eventmanagementservice.repository;

import com.beesocial.eventmanagementservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
