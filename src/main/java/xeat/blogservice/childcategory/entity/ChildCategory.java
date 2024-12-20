package xeat.blogservice.childcategory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.global.time.FullTimeEntity;
import xeat.blogservice.parentcategory.entity.ParentCategory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CHILD_CATEGORY")
public class ChildCategory extends FullTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_CATEGORY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PARENT_CATEGORY_ID", referencedColumnName = "PARENT_CATEGORY_ID")
    private ParentCategory parentCategory;

    @Column(name = "CHILD_NAME")
    private String childName;

    @Builder.Default
    @OneToMany(mappedBy = "childCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> childCategories = new ArrayList<>();

    public void updateChildName(String childName) {
        this.childName = childName;
    }
}
