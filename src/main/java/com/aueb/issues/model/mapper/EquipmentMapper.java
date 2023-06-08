package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.web.dto.EquipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface EquipmentMapper {
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);
    @Mapping(source = "entity.typeOfEquipment", target="typeOfEquipment")
    @Mapping(source = "entity.site.name",target = "siteName")
    @Mapping(source = "entity.site.building.name", target = "buildingName")
    public EquipmentDTO toDTO(EquipmentEntity entity);
}
