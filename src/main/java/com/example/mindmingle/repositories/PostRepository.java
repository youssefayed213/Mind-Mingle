package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
}
