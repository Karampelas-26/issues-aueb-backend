package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.CommentEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.web.dto.BuildingDTO;
import com.aueb.issues.web.dto.EquipmentDTO;
import com.aueb.issues.web.dto.CommentDTO;
import com.aueb.issues.web.dto.SiteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public  class MapperUtils {
    static
    BuildingMapper buildingMapper;
    static
    SiteMapper siteMapper;
    static
    EquipmentMapper equipmentMapper;
    public String map(Object object){
    static BuildingMapper buildingMapper;
    static SiteMapper siteMapper;
    static ApplicationMapper applicationMapper;
    public static String map(Object object){
        if(object!=null)
            return object.toString();
        return null;
    }
    public static EquipmentDTO mapEquipment(EquipmentEntity entity){
        if(equipmentMapper != null)
            return equipmentMapper.toDTO(entity);
        return null;
    }
    public static BuildingDTO mapBuilding(BuildingEntity entity){
        if(buildingMapper!=null)
            return buildingMapper.toDTO(entity);
        return null;
    }
    public static String mapUserName(UserEntity entity){
        if(entity==null) return null;
        return entity.getFirstname() + " " + entity.getLastname();
    }

    public static SiteDTO mapBuilding(SiteEntity entity){
        if(siteMapper!=null)
            return siteMapper.toDTO(entity);
        return null;
    }

    public static List<CommentDTO> mapCommentList(List<CommentEntity> entities){
        if(entities==null) return null;
        if(applicationMapper!=null){
            List<CommentDTO> ret=new ArrayList<>();
            for(CommentEntity c:entities)
                ret.add(applicationMapper.toCommentDTO(c));
            return ret;
        }
        return null;
    }
    @Autowired
    public void init(BuildingMapper buildingMapper, SiteMapper siteMapper,ApplicationMapper applicationMapper){
       MapperUtils.siteMapper=siteMapper;
       MapperUtils.buildingMapper=buildingMapper;
       MapperUtils.applicationMapper=applicationMapper;
       MapperUtils.equipmentMapper=equipmentMapper;

    }
}
