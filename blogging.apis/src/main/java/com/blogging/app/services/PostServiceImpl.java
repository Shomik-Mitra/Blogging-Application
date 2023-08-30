package com.blogging.app.services;

import com.blogging.app.entity.Category;
import com.blogging.app.entity.Post;
import com.blogging.app.entity.User;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payload.PostDto;
import com.blogging.app.payload.PostResponse;
import com.blogging.app.repositories.CategoryRepo;
import com.blogging.app.repositories.PostRepo;
import com.blogging.app.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer CategoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user"," ID",userId));
        Category category=this.categoryRepo.findById(CategoryId).orElseThrow(()->new ResourceNotFoundException("Category"," Id ",CategoryId));

        Post post=this.modelMapper.map(postDto,Post.class);
        post.setAddedDate(new Date());
        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);

        return this.modelMapper.map(this.postRepo.save(post),PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {
        Post post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post"," PostId ",id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        return this.modelMapper.map(this.postRepo.save(post),PostDto.class);
    }

    @Override
    public void DeletePost(Integer id){
    Post post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post"," PostId ",id));
    this.postRepo.delete(post);
    }
    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy) {
//        int pageNumber=1;
//        int pageSize=2;
        Pageable p= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> pagepost=this.postRepo.findAll(p);
        List<Post> post=pagepost.getContent();

        PostResponse postResponse=new PostResponse();
        List<PostDto> postDtos=post.stream().map(posts->this.modelMapper.map(posts,PostDto.class)).collect(Collectors.toList());
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagepost.getNumber());
        postResponse.setPageSize(pagepost.getSize());
        postResponse.setTotalElements((int)pagepost.getTotalElements());
        postResponse.setTotalPages(pagepost.getTotalPages());
        postResponse.setLastPage(pagepost.isLast());
       return postResponse;
    }

    @Override
    public PostDto getPostById(Integer id){
        Post post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post"," Id ",id));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostByUser(Integer userid) {
        User user=this.userRepo.findById(userid).orElseThrow(()->new ResourceNotFoundException("User"," Id ",userid));
        List<Post> posts=this.postRepo.findByUser(user);
        return posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByCategory(Integer cid) {
        Category category=this.categoryRepo.findById(cid).orElseThrow(()->new ResourceNotFoundException("Category"," Id ",cid));
        List<Post> li=this.postRepo.findByCategory(category);
           return li.stream().map(posts->this.modelMapper.map(posts,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPostByTitle(String title) {
        List<Post> posts=this.postRepo.SearchByTitle("%"+title+"%");
        return posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
}
