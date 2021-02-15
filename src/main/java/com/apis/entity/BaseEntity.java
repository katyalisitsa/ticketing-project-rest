package com.apis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false) //will assign date and time when 1st time only
    public LocalDateTime insertDateTime;

    @Column(nullable = false, updatable = false) //will assign date and time when 1st time only
    public Long insertUserId;

    public LocalDateTime lastUpdateDateTime;
    public Long lastUpdateUserId;

    public Boolean isDeleted=false;

// need to be override below in BaseEntityListener
//    @PrePersist //before save, runs following methods all the time
//    private void onPrePersist(){
//        this.insertDateTime=LocalDateTime.now();
//        this.lastUpdateDateTime=LocalDateTime.now();
//        //below hard coded, but will be dynamic when security added
//        this.insertUserId=1L;
//        this.lastUpdateUserId=1L;
//    }
//
//    @PreUpdate
//    private void onPreUpdate(){
//        this.lastUpdateDateTime=LocalDateTime.now();
//        this.lastUpdateUserId=1L;
//    }

}
