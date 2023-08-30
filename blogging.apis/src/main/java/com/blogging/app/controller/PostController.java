package com.blogging.app.controller;

import com.blogging.app.config.AppConstants;
import com.blogging.app.payload.ApiResponse;
import com.blogging.app.payload.PostDto;
import com.blogging.app.payload.PostResponse;
import com.blogging.app.services.FileService;
import com.blogging.app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;
    @Value("${project-image}")
    private String path;

    @PostMapping("/user/{userId}/category/{cid}/posts")
    private ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer cid
    ){
        return new ResponseEntity<>(this.postService.createPost(postDto,userId,cid), HttpStatus.CREATED );
    }

    @GetMapping("/user/{userId}/posts")
    private ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
      return new ResponseEntity<>(this.postService.getPostByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    private ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        return new ResponseEntity<>(this.postService.getPostByCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/posts")
    private ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_By,required = false) String sortBy
    ){
        return new ResponseEntity<>(this.postService.getAllPost(pageNumber,pageSize,sortBy),HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    private ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(this.postService.getPostById(postId),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    private ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        return new ResponseEntity<>(this.postService.updatePost(postDto,postId),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    private ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId){
        this.postService.DeletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully!!",true),HttpStatus.ACCEPTED);
    }

    //Search
    @GetMapping("/post/search/{title}")
    private ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String title){
        return new ResponseEntity<>(this.postService.searchPostByTitle(title),HttpStatus.OK);
    }

    //Upload Images
    @PostMapping("/post/image/upload/{postId}")
    private ResponseEntity<PostDto> uploadImageInPost(
            @RequestParam("image") MultipartFile file,
            @PathVariable Integer postId
            ) throws IOException {
        PostDto postdto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, file);
        postdto.setImageName(fileName);
        return new ResponseEntity<>(this.postService.updatePost(postdto,postId),HttpStatus.OK);
    }
}
