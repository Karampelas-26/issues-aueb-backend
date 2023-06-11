package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.NotificationsEntity;
import com.aueb.issues.web.dto.NotificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);
    @Mapping(source = "content", target = "content")
    @Mapping(source = "dateNotification", target = "dateNotification")
    public NotificationDTO toDTO(NotificationsEntity entity);
}
