package web.model.entity;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass//상속 전용 엔티티
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseTime {
    @CreatedDate
    private LocalDateTime create_at;

    @LastModifiedDate
    private LocalDateTime update_at;
}
