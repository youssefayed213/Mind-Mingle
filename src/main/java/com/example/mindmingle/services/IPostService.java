package com.example.mindmingle.services;

import com.example.mindmingle.entities.User;
import com.example.mindmingle.entities.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface IPostService {

    Post addPost(Post post);

    public Post addPostToUser(int userId, Post post);

    //Post updatePost(Post post);
    void removePost(Integer idPost, Integer userId);

    Post retrievePost(Integer idPost);

    List<Post> retreiveAllPosts();

    void updatePost(int idUser, int idPost, Post updatedPost);

    //////Like-Dislike////////

    void likePost(Post post, User user);

    public void dislikePost(Post post, User user);

    void unlikePost(Post post, User user);

    void undislikePost(Post post, User user);


    String getRecommendation();

    List<Post> getPostsSortedByLikes();
    String saveImage(MultipartFile imageFile);

    String addPostToUserImage(int userId, Post post, MultipartFile imageFile);
    void updatePost(int idUser, int idPost, Post updatedPost, MultipartFile imageFile);


    }