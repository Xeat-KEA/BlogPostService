package xeat.blogservice.global.time;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@MappedSuperclass // 다른 entity들이 클래스를 상속할 경우 BaseTimeEntity의 필드를 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 포함
public class FullTimeEntity extends CreatedTimeEntity {

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedDate;
}
