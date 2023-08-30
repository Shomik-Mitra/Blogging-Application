package com.blogging.app.payload;

import com.blogging.app.entity.Category;
import com.blogging.app.entity.Comment;
import com.blogging.app.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class PostDto {

    private String title;

    private String content;

    private Date addedDate;

    private String ImageName;

    private UserDto user;

    private CategoryDto category;

    private List<CommentDto> comments;

}
