package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert,Integer> {
}
