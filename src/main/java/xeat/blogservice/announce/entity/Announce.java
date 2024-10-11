package xeat.blogservice.announce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.CreatedTimeEntity;
import xeat.blogservice.global.FullTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "ANNOUNCE")
public class Announce extends FullTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANNOUNCE_ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;
}
