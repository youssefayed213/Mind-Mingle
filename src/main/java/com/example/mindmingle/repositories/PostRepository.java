package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Post findByIdPost(Integer PostId);
    @Query(nativeQuery = true, value = "SELECT type_post, SUM(likes), SUM(dislikes) FROM post GROUP BY type_post")
    List<Object[]> calculatePostStatistics();
    List<Post> findByOrderByLikesDesc();
    @Query("SELECT p FROM Post p JOIN p.user u WHERE p.idPost = :postId AND u.idUser = :userId")
    Optional<Post> findByIdPostAndUserId(@Param("postId") Integer idPost, @Param("userId") Integer userId);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.datePost >= :today")
    Long countPostsCreatedToday(@Param("today") Date today);
}
