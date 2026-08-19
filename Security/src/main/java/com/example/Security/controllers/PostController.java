package com.example.Security.controllers;


import com.example.Security.dto.PostDto;
import com.example.Security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    // this is the new field that we have for the secure access role
    // this time we will receive one kind of exception which is known as Access denied exception
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    // admin and user only able to enter this
    public List<PostDto> getAllPosts() {
        return postService.getAllDTO();
    }


    // second annotations
    @GetMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public PostDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDto createNewPost(@RequestBody PostDto inputPost) {
        return postService.createNewPost(inputPost);
    }

    @PutMapping("{postId}")
    public PostDto updatePost(@RequestBody PostDto inputPost,@PathVariable Long postId ) {
        return postService.updatePost(inputPost, postId);
    }


}