package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Commentaire;
import com.example.mindmingle.entities.Post;
import com.example.mindmingle.entities.TypePost;
import com.example.mindmingle.entities.User;

import com.example.mindmingle.repositories.PostRepository;
import com.example.mindmingle.repositories.UserRepository;
import com.example.mindmingle.services.IPostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/Posts")
public class PostController {
    IPostService postService;
    UserRepository userRepository;
    PostRepository postRepository;

    public static final String ACCOUNT_SID = "AC35e318f866203bf99b8eaccf9dfb5e7c";
    public static final String AUTH_TOKEN = "f7f61e99ed3afdd228b3b31e8b66ba48";

    @GetMapping("/{postId}")
    public Post retrievePost(@PathVariable Integer postId) {
        return postService.retrievePost(postId);
    }

    @GetMapping("/retrieve-all-posts")
    public List<Post> getPosts() {
        List<Post> listPosts = (List<Post>) postService.retreiveAllPosts();
        return listPosts;
    }

    @PostMapping("/add-Post")
    public Post addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }


    @PostMapping("/users/{userId}/add-Post")
    public Post addPostToUser(@PathVariable int userId, @RequestBody Post post) {
        // Récupérer l'utilisateur correspondant à l'ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId));

        // Associer l'utilisateur au post
        post.setUser(user);

        // Enregistrer le post dans la base de données
        return postService.addPost(post);
    }

    //    @PutMapping("/{postId}")
//    public ResponseEntity<Post> updatePost(@PathVariable int postId, @RequestBody Post updatedPost) {
//        try {
//            updatedPost.setIdPost(postId); // Assurez-vous que l'ID du post correspond à celui de l'URL
//            Post updated = postService.updatePost(updatedPost);
//            return ResponseEntity.ok(updated);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @PostMapping("/{idUser}/{idPost}")
    public ResponseEntity<String> updatePost(
            @PathVariable int idUser,
            @PathVariable int idPost,
            @RequestParam("titre") String titre,
            @RequestParam("description") String description,
            @RequestParam("typePost") String typePostStr, // Notez le suffixe 'Str'
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            // Convertir la chaîne de caractères en TypePost
            TypePost typePost = TypePost.valueOf(typePostStr.toUpperCase()); // Assurez-vous que la chaîne est en majuscules

            // Créer une nouvelle instance de Post avec les valeurs fournies
            Post updatedPost = new Post();
            updatedPost.setTitre(titre);
            updatedPost.setDescription(description);
            updatedPost.setTypePost(typePost); // Utiliser l'instance de TypePost

            // Passer la nouvelle instance de Post au service
            postService.updatePost(idUser, idPost, updatedPost, imageFile);
            return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            // Retourner le message d'erreur
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/posts/{idPost}/users/{idUser}")
    public String removePost(@PathVariable Integer idPost, @PathVariable Integer idUser) {
        try {
            postService.removePost(idPost, idUser);
            return "Post supprimé avec succès.";
        } catch (Exception e) {
            return "Erreur lors de la suppression du post.";
        }
    }


    ///////////////////Recomendation///////
    @GetMapping("/recommendation")
    public String getPostRecommendation() {
        return postService.getRecommendation();
    }
    ////////////Send-SMS////////////////

    void sendSmsToPostOwner(Commentaire commentaire) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        //String userPhoneNumber = commentaire.getPost().getUser().getTel();

        String messageContent = "Bonjour, un nouveau commentaire a été ajouté à votre post : " + commentaire.getContenu();

        // Envoyer le SMS avec le numéro de téléphone statique
        Message message = Message.creator(
                        new PhoneNumber("+21650734421"), // to
                        new PhoneNumber("+17607849453"), // from
                        messageContent)
                .create();

        System.out.println("SMS sent: " + message.getSid());
    }


    @GetMapping("/posts/sorted-by-likes")
    public List<Post> getPostsSortedByLikes() {
        return postService.getPostsSortedByLikes();
    }

//    @PostMapping("/{postId}/like")
//    public ResponseEntity<?> toggleLike(@PathVariable int postId, @RequestParam int userId) {
//        User user = userRepository.findById(userId).orElse(null);
//        Post post = postRepository.findById(postId).orElse(null);
//
//        if (post != null && user != null) {
//            if (user.hasLikedPost(post)) {
//                // Si l'utilisateur a déjà aimé le post, ne rien faire
//                return ResponseEntity.ok("Post already liked by the user");
//            } else {
//                if (user.hasDislikedPost(post)) {
//                    // Si l'utilisateur a précédemment détesté le post, annuler le dislike
//                    postService.undislikePost(post, user);
//                }
//                // L'utilisateur n'a pas encore aimé le post, lui permettre de liker
//                postService.likePost(post, user);
//                postRepository.save(post); // Sauvegarder les modifications
//
//                // Vérifier si le nombre de likes dépasse 10
//                if (post.getLikes() >= 10) {
//                    // Récupérer le numéro de téléphone du propriétaire du post
//                    String phoneNumber = post.getUser().getTel();
//                    String messageContent = "Votre post sur le thème '" + post.getTypePost() + "' est en tendance! Continuez comme ça en partageant plus sur ce sujet.";
//
//                    // Envoyer un SMS au propriétaire du post
//                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//                    Message message = Message.creator(
//                            new PhoneNumber("+21650734421"), // to
//                            new PhoneNumber("+19382014036"), // from
//                            messageContent
//                    ).create();
//                }
//
//                return ResponseEntity.ok("Post liked successfully");
//            }
//        } else {
//            return ResponseEntity.badRequest().body("Invalid post or user");
//        }
//    }
//
//
//    @PostMapping("/{postId}/dislike")
//    public ResponseEntity<?> toggleDislike(@PathVariable int postId, @RequestParam int userId) {
//        User user = userRepository.findById(userId).orElse(null);
//        Post post = postRepository.findById(postId).orElse(null);
//
//        if (post != null && user != null) {
//            if (user.hasDislikedPost(post)) {
//                // Si l'utilisateur a déjà désaimé le post, ne rien faire
//                return ResponseEntity.ok("Post already disliked by the user");
//            } else {
//                if (user.hasLikedPost(post)) {
//                    // Si l'utilisateur a précédemment aimé le post, annuler le like
//                    postService.unlikePost(post, user);
//                }
//                // L'utilisateur n'a pas encore désaimé le post, lui permettre de disliker
//                postService.dislikePost(post, user);
//                postRepository.save(post); // Sauvegarder les modifications
//
//                return ResponseEntity.ok("Post disliked successfully");
//            }
//        } else {
//            return ResponseEntity.badRequest().body("Invalid post or user");
//        }
//    }

//    @PostMapping("/{postId}/like")
//    public ResponseEntity<?> toggleLike(@PathVariable int postId) {
//        Post post = postRepository.findById(postId).orElse(null);
//
//        if (post != null) {
//            postService.likePost(post); // Appel à la méthode likePost pour effectuer l'action de like
//            return ResponseEntity.ok("Post liked successfully");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid post");
//        }
//    }
//
//
//    @PostMapping("/{postId}/dislike")
//    public ResponseEntity<?> toggleDislike(@PathVariable int postId) {
//        Post post = postRepository.findById(postId).orElse(null);
//
//        if (post != null) {
//            postService.dislikePost(post); // Appel à la méthode dislikePost pour effectuer l'action de dislike
//            return ResponseEntity.ok("Post disliked successfully");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid post");
//        }
//    }


    @PostMapping("/{idPost}/like/{idUser}")
    public ResponseEntity<?> toggleLike(@PathVariable int idPost, @PathVariable int idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        Post post = postRepository.findById(idPost).orElse(null);

        if (post != null && user != null) {
            if (user.hasLikedPost(post)) {
                // Si l'utilisateur a déjà aimé le post, ne rien faire
                return ResponseEntity.ok("Post already liked by the user");
            } else {
                if (user.hasDislikedPost(post)) {
                    // Si l'utilisateur a précédemment détesté le post, annuler le dislike
                    postService.undislikePost(post, user);
                }
                // L'utilisateur n'a pas encore aimé le post, lui permettre de liker
                postService.likePost(post, user);
                postRepository.save(post); // Sauvegarder les modifications

                // Vérifier si le nombre de likes dépasse 10
                if (post.getLikes() >= 2) {
                    // Récupérer le numéro de téléphone du propriétaire du post
                    String phoneNumber = post.getUser().getTel();
                    String messageContent = "Votre post sur le thème '" + post.getTypePost() + "' est en tendance! Continuez comme ça en partageant plus sur ce sujet.";

                    // Envoyer un SMS au propriétaire du post
                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    Message message = Message.creator(
                            new PhoneNumber("+21650734421"), // to
                            new PhoneNumber("+17607849453"), // from
                            messageContent
                    ).create();
                }

                return ResponseEntity.ok("Post liked successfully");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid post or user");
        }
    }

    @PostMapping("/{idPost}/dislike/{idUser}")
    public ResponseEntity<?> toggleDislike(@PathVariable int idPost, @PathVariable int idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        Post post = postRepository.findById(idPost).orElse(null);

        if (post != null && user != null) {
            if (user.hasDislikedPost(post)) {
                // Si l'utilisateur a déjà désaimé le post, ne rien faire
                return ResponseEntity.ok("Post already disliked by the user");
            } else {
                if (user.hasLikedPost(post)) {
                    // Si l'utilisateur a précédemment aimé le post, annuler le like
                    postService.unlikePost(post, user);
                }
                // L'utilisateur n'a pas encore désaimé le post, lui permettre de disliker
                postService.dislikePost(post, user);
                postRepository.save(post); // Sauvegarder les modifications

                return ResponseEntity.ok("Post disliked successfully");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid post or user");
        }


    }

    @GetMapping("/posts/top3")
    public List<Post> getTop3PostsByLikes() {
        // Appeler le service pour obtenir les posts triés par likes
        List<Post> sortedPosts = postService.getPostsSortedByLikes();

        // Si la liste des posts triés contient plus de 3 éléments, retourner les 3 premiers
        if (sortedPosts.size() >= 3) {
            return sortedPosts.subList(0, 3);
        } else {
            // Sinon, retourner la liste entière
            return sortedPosts;
        }

    }




//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
//        try {
//            String fileName = imageStorageService.storeImage(file);
//            return ResponseEntity.ok("Image uploaded successfully: " + fileName);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
//        }
//    }


    /////////////////Methodes Pour faire Le upload Image ///////////////
    @PostMapping("/upload")
    public String handleImageUpload(@RequestParam("image") MultipartFile file) {
        // Logique pour sauvegarder l'image
        String image = postService.saveImage(file);
        return "Image uploaded successfully. Image path: " + image;
    }



    ////////////ADDPost with image /////////////////





    @PostMapping("/users/{userId}/addPostToUserImage")
    public ResponseEntity<Post> addPostToUserImage(
            @PathVariable("userId") int userId,
            @RequestParam("titre") String titre,
            @RequestParam("description") String description,
            @RequestParam("typePost") String typePostStr,
            @RequestPart("image") MultipartFile imageFile) {

        Post post = new Post();
        post.setTitre(titre);
        post.setDescription(description);

        // Définissez la date de publication sur la date actuelle
        post.setDatePost(new Date());

        // Conversion de la chaîne de caractères en énumération
        TypePost typePost = TypePost.valueOf(typePostStr.toUpperCase());
        if (typePost == null) {
            throw new IllegalArgumentException("Invalid typePost value: " + typePostStr);
        }
        post.setTypePost(typePost);

        // Appelez la méthode du service pour ajouter le post avec l'image et récupérer le nom de l'image
        String imageName = postService.addPostToUserImage(userId, post, imageFile);

        // Attribuez le nom de l'image à l'entité Post
        post.setImage(imageName);

        // Sauvegardez le post dans la base de données
        post = postRepository.save(post);

        // Retournez le post sauvegardé dans la réponse
        return ResponseEntity.ok(post);
    }





}