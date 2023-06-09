package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.web.dto.EquipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface EquipmentMapper {
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);
    @Mapping(source = "entity.id",target = "id")
    @Mapping(source = "entity.typeOfEquipment", target="typeOfEquipment")
    public EquipmentDTO toDTO(EquipmentEntity entity);
}
