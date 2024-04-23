package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission,Integer> {
}
