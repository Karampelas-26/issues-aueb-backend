package com.aueb.issues.model.mapper;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.web.dto.BuildingDTO;
import com.aueb.issues.web.dto.EquipmentDTO;
import com.aueb.issues.web.dto.SiteDTO;
import com.opencsv.bean.function.AccessorInvoker;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public  class MapperUtils {
    static
    BuildingMapper buildingMapper;
    static
    SiteMapper siteMapper;
    static
    EquipmentMapper equipmentMapper;
    public String map(Object object){
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

    public static SiteDTO mapBuilding(SiteEntity entity){
        if(siteMapper!=null)
            return siteMapper.toDTO(entity);
        return null;
    }
    @Autowired
    public void init(BuildingMapper buildingMapper, SiteMapper siteMapper){
       MapperUtils.siteMapper=siteMapper;
       MapperUtils.buildingMapper=buildingMapper;
       MapperUtils.equipmentMapper=equipmentMapper;

    }
}
