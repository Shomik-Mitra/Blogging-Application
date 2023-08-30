package com.blogging.app.controller;

import com.blogging.app.payload.ApiResponse;
import com.blogging.app.payload.CommentDto;
import com.blogging.app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    private ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        return new ResponseEntity<>(this.commentService.createComment(commentDto,postId), HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    private ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment is deleted succussfully!!",true),HttpStatus.ACCEPTED);
    }
}
