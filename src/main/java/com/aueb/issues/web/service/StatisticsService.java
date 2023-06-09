package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.repository.BuildingRepository;
import com.aueb.issues.web.dto.InnerStatPojo;
import com.aueb.issues.web.dto.OuterStatPojo;
import jakarta.persistence.TemporalType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class StatisticsService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    BuildingRepository buildingRepository;
    public ResponseEntity<List<Map<String, Object>>> getAllStatsByMonth(List<Long> buildingIds, String issueType, LocalDateTime creationStart, LocalDateTime creationEnd){
        if(issueType!=null  && ! (issueType.equals(IssueType.CLIMATE_CONTROL.name())||issueType.equals(IssueType.ELECTRICAL.name())||
                issueType.equals(IssueType.EQUIPMENT.name())||issueType.equals(IssueType.INFRASTRUCTURE.name()))){
            log.error("Incorrect IssueType");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M");
        if(buildingIds==null)
            buildingIds=buildingRepository.findAll().stream().map(BuildingEntity::getId).toList();
        if(creationStart==null)
            creationStart=LocalDateTime.of(2020,1,1,0,0);
        if(creationEnd==null)
            creationEnd=LocalDateTime.now();

        String[] startingDate =creationStart.format(formatter).split("-");
        String[] endingDate= creationEnd.format(formatter).split("-");
        List<String> labels=new ArrayList<>();
        int i=Integer.parseInt(startingDate[1]);
        int year=Integer.parseInt(startingDate[0]);
        while (true){
            for(; i<=12; i++){
                labels.add(year+"-"+i);
                if(year==Integer.parseInt(endingDate[0])&&i==Integer.parseInt(endingDate[1]))
                    break;
            }
            i=1;
            if(year==Integer.parseInt(endingDate[0]))
                break;
            else year++;
        }
        Map<String, Integer> positions=new HashMap<>();
        for(i=0;i<labels.size();i++){
            positions.put(labels.get(i), i);
        }

        List<Long> finalBuildingIds = buildingIds;
        List<Map<String, Object>> results=applicationRepository.countApplicationsByMonthWithFilters(creationStart,creationEnd,issueType)
                .stream().filter(result-> finalBuildingIds.contains(result.get("id"))).toList();
        OuterStatPojo ret=new OuterStatPojo(labels, new ArrayList<>());
        for(Long l:buildingIds){
            ret.getInnerStatPojos().add(new InnerStatPojo(getNameOfBuilding(l),new ArrayList<>(labels.size())));
        }
        for(Map<String,Object> r:results){
            String name=getNameOfBuilding((Long) r.get("id"));
            if(name!=null){
                Integer indx=getIndexOfInnerStat(ret.getInnerStatPojos(),name);
                InnerStatPojo pojo=ret.getInnerStatPojos().get(indx);
                pojo.getData().add(positions.get(r.get("monthnumber"))-1,Long.parseLong(r.get("count").toString()));
            }
        }
        log.info(ret.toString());

        return new ResponseEntity<>(applicationRepository.countApplicationsByMonthWithFilters(creationStart,creationEnd,issueType), HttpStatus.OK);
    }

    private String getNameOfBuilding(Long i){
        Optional<BuildingEntity> b= buildingRepository.getBuildingById(i);
        return b.map(BuildingEntity::getName).orElse(null);
    }

    private Integer getIndexOfInnerStat(List<InnerStatPojo> list, String name){
        List<InnerStatPojo> ret=list.stream().filter(innerStatPojo ->
            innerStatPojo.getLabel().equals(name)
        ).toList();
        if(ret.size()==1) return list.indexOf(ret.get(0));
        else return null;
    }
}
