package com.blogging.app.repositories;

import com.blogging.app.entity.Category;
import com.blogging.app.entity.Post;
import com.blogging.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select u from Post u where u.title like :key")
    List<Post> SearchByTitle(@Param("key") String title);
}
