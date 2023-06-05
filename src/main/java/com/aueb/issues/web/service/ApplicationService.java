package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Role;
import com.aueb.issues.model.enums.Status;
import com.aueb.issues.model.mapper.ApplicationMapper;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    SiteService siteService;
    @Autowired
    BuildingService buildingService;

    public ResponseEntity<List<TeacherApplicationsDTO>> getTeacherApplications(){
        List<TeacherApplicationsDTO> ret = new ArrayList<>();
        try{
            List<ApplicationEntity> issues = applicationRepository.findAll();
            ObjectMapper mapper = new ObjectMapper();
            for (ApplicationEntity issue: issues){
                mapper.convertValue(issue,TeacherApplicationsDTO.class);
            }
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return (ResponseEntity<List<TeacherApplicationsDTO>>) ret;
    }
    public ResponseEntity<String> submitApplication(ApplicationDTO requestDTO){
        //TODO: Valdidate request and create new entity
        return  ResponseEntity.ok(null);
    }

    public ResponseEntity<List<ApplicationDTO>> getAllApplications(){
        List<ApplicationDTO> ret = new ArrayList<>();
        try{
            List<ApplicationEntity> issues = applicationRepository.findAll();
            ret=issues.stream().map(ApplicationMapper.INSTANCE::toDTO).toList();
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }
    //todo: include calling user in parameeters and find issueType and assignee tech from there
    //discuss if createdUser should be found by name or by ID
    public ResponseEntity<List<ApplicationDTO>> getApplicationsBySingleValues(String role, UserEntity userEntity, String siteName, String priority, String issueType, String status, String buildingName){
        try {

            if(priority!=null && !(priority.equals(Priority.LOW.name())||priority.equals(Priority.HIGH.name())||priority.equals(Priority.MEDIUM.name()))){
                log.error("Incorrect Priority");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            if(issueType!=null  && ! (issueType.equals(IssueType.CLIMATE_CONTROL.name())||issueType.equals(IssueType.ELECTRICAL.name())||
                    issueType.equals(IssueType.EQUIPMENT.name())||issueType.equals(IssueType.INFRASTRUCTURE.name()))){
                log.error("Incorrect IssueType");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if(status!=null && !(status.equals(Status.CREATED.name())||status.equals(Status.REJECTED.name())||
                    status.equals(Status.VALIDATED.name())||status.equals(Status.ASSIGNED.name())||status.equals(Status.COMPLETED.name())
                    ||status.equals(Status.ARCHIVED.name()))){
                log.error("Incorrect Status");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            List<Status> exStatus=new ArrayList<>();
            if(userEntity.getRole().equals(Role.TECHNICIAN)&&userEntity.getTechnicalTeam()!=null){
                issueType=userEntity.getTechnicalTeam().name();
            }
            switch (role){
                case "TECHNICIAN":
                    exStatus.add(Status.REJECTED);
                    exStatus.add(Status.CREATED);
                    exStatus.add(Status.ARCHIVED);
                    break;
                case "COMMITTEE":
                    exStatus=null;
                    break;
                default:
                    exStatus.add(Status.ARCHIVED);
                    break;
            }


            List<ApplicationEntity> q = applicationRepository.findByValues(siteName,
                    priority==null?null:Priority.valueOf(priority),
                    issueType==null?null:IssueType.valueOf(issueType),
                    ,
                    ,
                    status==null?null:Status.valueOf(status),
                    buildingName,exStatus);

            return ResponseEntity.ok(toDTO(q));
        }
        catch (Exception e){
            log.error(e.toString());
        }
        return ResponseEntity.internalServerError().body(null);
    }
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByBuilding(String buildingName){
        List<ApplicationEntity> entities=applicationRepository.findByBuildingName(buildingName);
        return ResponseEntity.ok(toDTO(entities));
    }

    public ResponseEntity<String> deleteIssue(String id){
        try{
            applicationRepository.findById(id).orElseThrow(()->{new EntityNotFoundException("No such entity found");
                return null;
            });
            applicationRepository.deleteById(id);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public ResponseEntity<String> updateApplication(String id, ApplicationDTO request){
        try{

//            ApplicationEntity issue = applicationRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Application not found"));
//            if(issue!=null) {
//                return ResponseEntity.badRequest().body("No issue with such id");
//            }
//            if(request.getTitle()!=null)
//                issue.setTitle(request.getTitle());
//            if(request.getPriority()!=null)
//                issue.setPriority(request.getPriority());
//            if(request.getSiteId()!=null)
////                issue.setSite(siteService.getSiteBySiteId(request.getSiteId()));
//            if(request.getBuildingId()!=0)
////                issue.setBuilding(buildingService.getBuildingById((request.getBuildingId())));
//            if(request.getDueDate()!=null)
//                issue.setCompletionDate(request.getDueDate());
            return ResponseEntity.ok(null);

        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public ResponseEntity<Map<String,List<String>>> getStaticData(){
        Map<String,List<String>> ret=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        ret.put("IssueTypes",mapper.convertValue(IssueType.values(),List.class));
        ret.put("Status",mapper.convertValue(Status.values(),List.class));
        ret.put("Priotity",mapper.convertValue(Priority.values(), List.class));
        return ResponseEntity.ok(ret);

    }

    public List<ApplicationDTO> toDTO(List<ApplicationEntity> entities){

        return entities.stream().map(ApplicationMapper.INSTANCE::toDTO).toList();
    }
}