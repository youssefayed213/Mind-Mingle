package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective,Integer> {
}
