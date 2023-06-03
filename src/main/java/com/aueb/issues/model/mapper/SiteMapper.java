package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.web.dto.SiteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SiteMapper {

    SiteMapper INSTANCE = Mappers.getMapper(SiteMapper.class);
    @Mapping(source ="entity.id",target ="id" )
    @Mapping(source ="entity.name",target = "name")
    @Mapping(source = "entity.floor", target = "floor")
    @Mapping(expression = "java(MapperUtils.mapBuilding(entity.getBuilding()))",target = "building")
    public SiteDTO toDTO(SiteEntity entity);
}
