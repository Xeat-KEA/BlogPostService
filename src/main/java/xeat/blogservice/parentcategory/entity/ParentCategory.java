package xeat.blogservice.parentcategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.time.FullTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PARENT_CATEGORY")
@DynamicUpdate
public class ParentCategory extends FullTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARENT_CATEGORY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "BLOG_ID")
    private Blog blog;

    @Column(name = "PARENT_NAME")
    @NotNull
    private String parentName;

    @Builder.Default
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.REMOVE)
    private List<ChildCategory> childCategories = new ArrayList<>();

    public void updateParentName(String parentName) {
        this.parentName = parentName;
    }

}
