package com.example.Security.services;
import com.example.Security.dto.PostDto;
import com.example.Security.entities.PostEntity;
import com.example.Security.entities.UserEntity;
import com.example.Security.exceptions.ResourceNotFoundException;
import com.example.Security.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImple implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @Override
    public List<PostDto> getAllDTO() {
        List<PostEntity> posts = postRepository.findAll();

        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);

        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

        // if  we decalre the resource not found exception so why we are going to use the runtime exception
        @Override
        public PostDto getPostById(Long postId) {
            UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("Logged in user: {}", user);

            PostEntity postEntity = postRepository.findById(postId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Post not found with id: " + postId));

            return modelMapper.map(postEntity, PostDto.class);
        }

    @Override
    public PostDto updatePost(PostDto inputPost, Long postId) {


        return null;
    }
}