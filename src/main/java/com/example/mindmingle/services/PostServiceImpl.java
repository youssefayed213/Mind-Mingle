package com.example.mindmingle.services;

import com.example.mindmingle.entities.Commentaire;
import com.example.mindmingle.entities.Post;
import com.example.mindmingle.entities.User;

import com.example.mindmingle.repositories.CommentaireRepository;
import com.example.mindmingle.repositories.PostRepository;
import com.example.mindmingle.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Date;


@Service
@Slf4j
@AllArgsConstructor
public class PostServiceImpl implements IPostService {
    private static final int SEUIL_RECOMMANDATION = 1;
    PostRepository postRepository;
    UserRepository userRepository;
    CommentaireRepository commentaireRepository;


    @Override
    public Post addPost(Post post) {

        // Obtenir la date et l'heure actuelles dans le fuseau horaire local
        LocalDateTime localDateTime = LocalDateTime.now();

        // Convertir LocalDateTime en Date avec le fuseau horaire local
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        // Définir la date du post
        post.setDatePost(date);
        return postRepository.save(post);
    }

    @Override
    public Post addPostToUser(int userId, Post post) {
        return null;
    }




    @Override
    @Transactional
    public void removePost(Integer idPost, Integer idUser) {
        // Vous devrez récupérer l'utilisateur correspondant à l'ID de l'utilisateur depuis votre service utilisateur ou votre base de données
        Optional<User> optionalUser = userRepository.findById(idUser);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<Post> optionalPost = postRepository.findById(idPost);

            // Vérifier si le post existe
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();

                // Vérifier si l'utilisateur actuel est le propriétaire du post
                if (post.getUser().equals(user)) {
                    postRepository.deleteById(idPost);
                    System.out.println("Post supprimé avec succès.");
                } else {
                    System.out.println("Vous n'êtes pas autorisé à supprimer ce post.");
                }
            } else {
                System.out.println("Le post spécifié n'existe pas.");
            }
        } else {
            System.out.println("L'utilisateur spécifié n'existe pas.");
        }
    }









    @Override
    public Post retrievePost(Integer idPost) {
        return postRepository.findById( idPost).orElse(null);
        //        return skierRepository.findById( numSkier).get;
        //car il peut ne rien retourner
    }






    @Override
    public List<Post> retreiveAllPosts() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            // Récupérez le nom de l'image associée à la publication
            String imageName = post.getImage();

        }
        return posts;
    }

    @Override
    public void updatePost(int idUser, int idPost, Post updatedPost) {

    }


    @Override
    public void updatePost(int idUser, int idPost, Post updatedPost, MultipartFile imageFile) {
        // Vérifier si l'utilisateur actuel est le propriétaire du post
        Optional<Post> existingPostOptional = postRepository.findByIdPostAndUserId(idPost, idUser);

        if (existingPostOptional.isPresent()) {
            // Mettre à jour le post
            Post existingPost = existingPostOptional.get();
            existingPost.setTitre(updatedPost.getTitre());
            existingPost.setDescription(updatedPost.getDescription());
            existingPost.setTypePost(updatedPost.getTypePost());
            // Mettre à jour la date du post avec la date actuelle
            existingPost.setDatePost(new Date());

            // Vérifier si une nouvelle image est fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                // Supprimer l'ancienne image si elle existe
                if (existingPost.getImage() != null) {
                    deleteImage(existingPost.getImage());
                }
                // Télécharger et enregistrer la nouvelle image
                String newImageName = saveImage(imageFile);
                // Mettre à jour le nom de l'image dans le post
                existingPost.setImage(newImageName);
                existingPost.setDatePost(new Date());

            }

            postRepository.save(existingPost);
        } else {
            throw new RuntimeException("User is not the owner of the post or post not found.");
        }
    }





    /////////////////Like-Dislike //////////////////////


    @Override
    public void likePost(Post post, User user) {
        if (!user.hasLikedPost(post)) {
            // Si l'utilisateur n'a pas déjà aimé le post
            if (user.hasDislikedPost(post)) {
                // S'il a précédemment désaimé le post, annuler le dislike
                undislikePost(post, user);
            }
            post.setLikes(post.getLikes() + 1);
            user.addLikedPost(post);
            postRepository.save(post);
            userRepository.save(user);
        }
    }

    @Override
    public void dislikePost(Post post, User user) {
        if (!user.hasDislikedPost(post)) {
            // Si l'utilisateur n'a pas déjà désaimé le post
            if (user.hasLikedPost(post)) {
                // S'il a précédemment aimé le post, annuler le like
                unlikePost(post, user);
            }
            post.setDislikes(post.getDislikes() + 1);
            user.addDislikedPost(post);
            postRepository.save(post);
            userRepository.save(user);
        }
    }


    @Override
    public void unlikePost(Post post, User user) {
        if (user.hasLikedPost(post)) {
            post.setLikes(post.getLikes() - 1);
            user.removeReaction(post);
            postRepository.save(post);
            userRepository.save(user);
        }
    }

    @Override
    public void undislikePost(Post post, User user) {
        if (user.hasDislikedPost(post)) {
            post.setDislikes(post.getDislikes() - 1);
            user.removeReaction(post);
            postRepository.save(post);
            userRepository.save(user);
        }
    }



    @Override
    public String getRecommendation() {
        // Obtenir tous les posts
        List<Post> allPosts = postRepository.findAll();

        // Trier les posts par nombre de likes et de commentaires
        List<Post> sortedPosts = allPosts.stream()
                .sorted(Comparator.comparingInt(post -> post.getLikes() + post.getCommentaires().size()))
                .collect(Collectors.toList());

        // Prendre le post avec le nombre de likes et commentaires le plus élevé
        Post recommendedPost = sortedPosts.get(sortedPosts.size() - 1);

        // Générer la recommandation
        String recommendation = "Recruter des experts dans le domaine de " + recommendedPost.getTypePost() + ". " +
                "Ce post a le plus grand nombre de likes et de commentaires.";

        try {
            // Convertir la recommandation en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(recommendation);
        } catch (Exception e) {
            // Gérer les erreurs de sérialisation JSON
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Post> getPostsSortedByLikes() {
        return postRepository.findByOrderByLikesDesc();
    }

    public static final String IMAGE_UPLOAD_DIR = "C:\\piCloud\\Mind-MingleFront\\src\\assets\\Front\\images\\blog\\";





    @Override
    public String addPostToUserImage(int userId, Post post, MultipartFile imageFile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setUser(user);

        // Téléchargez et stockez l'image dans un répertoire local
        String imageName = saveImage(imageFile);

        // Attribuez le nom de l'image à l'entité Post
        post.setImage(imageName);

        // Assurez-vous que le titre, la description et le type de post sont définis
        if (post.getTitre() == null || post.getDescription() == null || post.getTypePost() == null) {
            throw new RuntimeException("Title, description, and type must be provided");
        }

        // Retournez simplement le nom de l'image
        return imageName;
    }

    public String saveImage(MultipartFile imageFile) {
        try {
            // Générez un nom unique pour l'image, par exemple en utilisant UUID
            String imageName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
            // Définissez le chemin complet où l'image sera stockée localement
            String directoryPath = "C:\\piCloud\\Mind-MingleFront\\src\\assets\\Front\\images\\blog\\";

            File directory = new File(directoryPath);

            // Vérifiez si le répertoire existe, sinon créez-le
            if (!directory.exists()) {
                directory.mkdirs(); // Créez les répertoires parents si nécessaire
            }

            String imagePath = directoryPath + imageName;
            // Enregistrez l'image localement
            Files.copy(imageFile.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
            return imageName; // Retournez le nom de l'image
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }


    // Méthode pour supprimer une image
    private void deleteImage(String imageName) {
        // Chemin complet de l'image
        String imagePath = "C:\\piCloud\\Mind-MingleFront\\src\\assets\\Front\\images\\blog\\" + imageName;
        // Créez un objet File pour l'image
        File imageFile = new File(imagePath);
        // Vérifiez si le fichier existe et s'il peut être supprimé
        if (imageFile.exists() && imageFile.isFile()) {
            if (!imageFile.delete()) {
                throw new RuntimeException("Failed to delete image: " + imageName);
            }
        } else {
            throw new RuntimeException("Image not found: " + imageName);
        }
    }





}