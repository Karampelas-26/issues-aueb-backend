package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.web.dto.BuildingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    BuildingMapper INSTANCE = Mappers.getMapper(BuildingMapper.class);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address",target = "address")
    @Mapping(source = "floors", target = "floors")
    public BuildingDTO toDTO(BuildingEntity entity);
}
