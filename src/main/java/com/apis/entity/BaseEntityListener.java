package com.apis.entity;

import com.apis.entity.common.UserPrincipal;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class BaseEntityListener extends AuditingEntityListener {
    //AuditingEntityListener from Spring
    @PrePersist
    private void onPrePersist(BaseEntity baseEntity) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.insertDateTime = LocalDateTime.now();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();
        baseEntity.insertUserId = 1L;
        baseEntity.lastUpdateUserId = 1L;
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            //if authentication is not null and user name is valid
            long id = Long.parseLong(authentication.getName());
            baseEntity.insertUserId = id;
            baseEntity.lastUpdateUserId = id;
        }

    }

    @PreUpdate
    void preUpdate(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();
        baseEntity.lastUpdateUserId = 1L;

        if (authentication != null & !authentication.getName().equals("anonymousUser")) {
            long id = Long.parseLong(authentication.getName());
            baseEntity.lastUpdateUserId = id;
        }
    }
}
