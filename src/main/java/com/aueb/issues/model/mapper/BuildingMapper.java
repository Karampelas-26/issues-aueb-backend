package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.web.dto.BuildingDTO;
import com.aueb.issues.web.dto.SiteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    BuildingMapper INSTANCE = Mappers.getMapper(BuildingMapper.class);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address",target = "address")
    @Mapping(source = "floors", target = "floors")
    public BuildingDTO toDTO(BuildingEntity entity);

    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.address",target = "address")
    @Mapping(source = "entity.floors", target = "floors")
    @Mapping(source = "sites", target = "sites")
    public BuildingDTO toDTOWithSites(BuildingEntity entity, List<SiteDTO> sites);
}
