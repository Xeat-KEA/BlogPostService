package xeat.blogservice.parentcategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import xeat.blogservice.global.CreatedTimeEntity;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.FullTimeEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "PARENT_CATEGORY")
@DynamicUpdate
public class ParentCategory extends FullTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARENT_CATEGORY_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "BLOG_ID")
    private Blog blog;

    @Column(name = "PARENT_NAME")
    @NotNull
    private String parentName;

    public void updateParentName(String parentName) {
        this.parentName = parentName;
    }

}
