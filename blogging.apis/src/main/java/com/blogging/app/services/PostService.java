package com.blogging.app.services;

import com.blogging.app.entity.User;
import com.blogging.app.payload.PostDto;
import com.blogging.app.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer CategoryId);

    PostDto updatePost(PostDto postDto,Integer id);

    void DeletePost(Integer id);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy);

    PostDto getPostById(Integer id);

    List<PostDto> getPostByUser(Integer userid);

    List<PostDto> getPostByCategory(Integer cid);

    List<PostDto> searchPostByTitle(String title);
}
