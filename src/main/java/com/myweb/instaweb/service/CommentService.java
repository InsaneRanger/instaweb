package com.myweb.instaweb.service;




/*
 * @author
 * @version
 * @return
 */


import com.myweb.instaweb.dto.CommentDTO;
import com.myweb.instaweb.entity.Comment;
import com.myweb.instaweb.entity.Post;
import com.myweb.instaweb.entity.User;
import com.myweb.instaweb.exceptions.PostNotFoundException;
import com.myweb.instaweb.repository.CommentRepository;
import com.myweb.instaweb.repository.PostRepository;
import com.myweb.instaweb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * SAVE COMMENT
     * */
    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFoundException("Post cannot be found by username:  "+ user.getEmail()));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());
        log.info("Saving comment for Post: {}", post.getId());
        return commentRepository.save(comment);
    }

    /**
     * GET ALL COMMENTS
     * */
    public List<Comment> getAllCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFoundException("Post cannot be found"));
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments;
    }

    /**
     * DELETE COMMENT
     * */

    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }

    /**
     * Helps method
     * */
    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found with username "+username));
    }


}
