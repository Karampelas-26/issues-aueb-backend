package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.CommentEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public  class MapperUtils {
    static BuildingMapper buildingMapper;
    static SiteMapper siteMapper;
    static EquipmentMapper equipmentMapper;
    static ApplicationMapper applicationMapper;
    static UserMapper userMapper;

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

    public static UserDTO mapCommentUser(UserEntity user){
        if(user==null) return null;
        return userMapper.toDto(user);
    }

    @Autowired
    public void init(BuildingMapper buildingMapper, SiteMapper siteMapper,ApplicationMapper applicationMapper, UserMapper userMapper){
       MapperUtils.siteMapper=siteMapper;
       MapperUtils.buildingMapper=buildingMapper;
       MapperUtils.applicationMapper=applicationMapper;
       MapperUtils.userMapper=userMapper;


    }
}
