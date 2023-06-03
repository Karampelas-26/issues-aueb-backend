package com.aueb.issues.web.dto;

import com.aueb.issues.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "role", target = "role")
    UserDTO toDto(UserEntity userEntity);
}
