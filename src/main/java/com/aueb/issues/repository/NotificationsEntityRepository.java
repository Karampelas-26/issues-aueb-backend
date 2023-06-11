package com.aueb.issues.repository;

import com.aueb.issues.model.entity.NotificationsEntity;
import com.aueb.issues.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsEntityRepository extends JpaRepository<NotificationsEntity, String> {
    public List<NotificationsEntity> findNotificationsEntitiesByUser(UserEntity user);
}