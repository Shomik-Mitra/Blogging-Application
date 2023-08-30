package com.blogging.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Categories")
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name="Title",length = 100,nullable = false)
    private String categoryTitle;
    @Column(name="Description")
    private String categoryDescription;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts;
}
