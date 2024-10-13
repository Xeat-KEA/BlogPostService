package xeat.blogservice.childcategory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.CreatedTimeEntity;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.FullTimeEntity;
import xeat.blogservice.parentcategory.entity.ParentCategory;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CHILD_CATEGORY")
public class ChildCategory extends FullTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_CATEGORY_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "BLOG_ID")
    private Blog blog;

    @ManyToOne
    private ParentCategory parentCategory;

    @Column(name = "CHILD_NAME")
    private String childName;
}
