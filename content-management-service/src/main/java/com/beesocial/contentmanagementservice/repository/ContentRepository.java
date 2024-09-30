package com.beesocial.contentmanagementservice.repository;

import com.beesocial.contentmanagementservice.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID> {
}
