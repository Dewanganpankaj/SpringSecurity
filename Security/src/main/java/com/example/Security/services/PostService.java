package com.example.Security.services;

import com.example.Security.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    // declare the class that we have

    List<PostDto> getAllDTO();
    // post mapping rule
    PostDto createNewPost(PostDto inputPost);
    // get mapping rule
    PostDto getPostById(Long postId);
    // put mapping rule
    PostDto updatePost(PostDto inputPost, Long postId);



}