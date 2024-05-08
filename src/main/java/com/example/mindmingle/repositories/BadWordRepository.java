package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.BadWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadWordRepository extends JpaRepository<BadWord,Long> {
}
