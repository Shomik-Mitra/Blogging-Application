package com.blogging.app.repositories;

import com.blogging.app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {


}
