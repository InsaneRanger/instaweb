package com.myweb.instaweb.service;




/*
 * @author
 * @version
 * @return
 */

import com.myweb.instaweb.dto.PostDTO;
import com.myweb.instaweb.entity.ImageModel;
import com.myweb.instaweb.entity.Post;
import com.myweb.instaweb.entity.User;
import com.myweb.instaweb.exceptions.PostNotFoundException;
import com.myweb.instaweb.repository.ImageRepository;
import com.myweb.instaweb.repository.PostRepository;
import com.myweb.instaweb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;


    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }



    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);
        log.info("Saving Post from User: {}", user.getEmail());

        return postRepository.save(post);
    }

    /**
     * Get ALL posts
     * */

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    /**
     *  GET POST BY ID
     * */


    public Post getPostById(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be find by username:  " + user.getEmail()));

    }

    /**
     *  GET ALL POST BY USER
     * */

    public List<Post> getAllPostForUser(Principal principal) {
        User user = getUserByPrincipal(principal);

        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    /**
     * Likes logic
     * */

    public Post likePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post cannot found!"));
        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(user -> user.equals(username)).findAny();
        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }

        return postRepository.save(post);
    }

    /**
     * Delete post by ID.
     * */

    public void deletePost(Long postId, Principal principal) {
        Post post = getPostById(postId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
    }



    /**
     * HELPS METHOD TO FIND User from Principal
     * Principal is interface to represent any entity an login id.....Auth Spring BOOT implements Principal
     * */

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }
}
