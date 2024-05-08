package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Integer> {
}
