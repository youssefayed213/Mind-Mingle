package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.Post;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.entities.UserPostReaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPostReactionRepository extends JpaRepository<UserPostReaction,Long> {


    @Query("DELETE FROM UserPostReaction upr WHERE upr.user = :user AND upr.post = :post")
    void deleteByUserAndPost(@Param("user") User user, @Param("post") Post post);

}

