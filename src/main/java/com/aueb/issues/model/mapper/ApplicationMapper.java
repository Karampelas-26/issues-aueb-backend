package com.aueb.issues.model.mapper;


import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ApplicationMapper {
    ApplicationMapper INSTANCE =Mappers.getMapper(ApplicationMapper.class);
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.title", target = "title")
    @Mapping(expression= "java(entity.getSite().getName())", target = "siteName")
    @Mapping(expression = "java(entity.getSite().getBuilding().getName())", target = "buildingName")
    @Mapping(expression = "java(entity.getPriority().name())", target = "priority")
    @Mapping(source = "entity.status", target = "status")
    @Mapping(source = "entity.description", target = "description")
    @Mapping(expression = "java(entity.getIssueType().name())", target = "issueType")
    @Mapping(source ="entity.dueDate" ,target="dueDate")
    @Mapping(source = "entity.createDate", target ="createDate")
    public ApplicationDTO toDTO(ApplicationEntity entity);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.title", target = "title")
    @Mapping(expression= "java(entity.getSite().getName())", target = "siteName")
    @Mapping(source = "entity.status", target = "status")
    public TeacherApplicationsDTO toTeacherDTO(ApplicationEntity entity);

}
