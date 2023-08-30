package com.blogging.app.services;

import com.blogging.app.entity.Comment;
import com.blogging.app.entity.Post;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payload.CommentDto;
import com.blogging.app.repositories.CommentRepo;
import com.blogging.app.repositories.PostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepo postRepo;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post"," Id ",postId));
        Comment comment=this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        return this.modelMapper.map(this.commentRepo.save(comment),CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
    Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment"," Id ",commentId));
    this.commentRepo.delete(comment);
    }
}
