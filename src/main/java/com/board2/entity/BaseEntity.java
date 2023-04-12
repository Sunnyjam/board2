package com.board2.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
//시간 정보를 다루는 부분
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp //생성되었을 때 시간정보를 만들어주는 옵션
    @Column(updatable = false) //수정시에는 관여를 안하게끔
    private LocalDateTime createdTime;

    @UpdateTimestamp //업데이트 발생시 시간정보를 주는 부분
    @Column(insertable = false)// 입력시(insert 시) 관여를 안하는 옵션
    private LocalDateTime updatedTime;
}
