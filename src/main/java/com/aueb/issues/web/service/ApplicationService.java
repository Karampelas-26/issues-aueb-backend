package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.entity.EquipmentEntity;
import com.aueb.issues.model.entity.SiteEntity;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Status;
import com.aueb.issues.model.mapper.ApplicationMapper;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.repository.EquipmentRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jdi.request.InvalidRequestStateException;
import io.jsonwebtoken.InvalidClaimException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SiteService siteService;
    @Autowired
    SitesRepository sitesRepository;
    @Autowired
    BuildingService buildingService;
    @Autowired
    EquipmentRepository equipmentRepository;

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
    public ResponseEntity<String> submitApplication(ObjectNode node, UserEntity user){

        String title =(node.get("title"))!=null?node.get("title").asText():null;
        String siteId =(node.get("siteId"))!=null?node.get("siteId").asText():null;
        Optional<SiteEntity> site=sitesRepository.findSiteById(String.valueOf(siteId));
        IssueType issueType =(node.get("issueType"))!=null?IssueType.valueOf(node.get("issueType").asText()):null;
        String equipmentId =(node.get("equipment"))!=null?node.get("equipment").asText():null;
        node.get("siteName");
        node.get("issueType");
        node.get("equipment");

        Optional<EquipmentEntity> equipmentEntity=equipmentRepository.findById(equipmentId);
        ApplicationEntity newEntity=ApplicationEntity.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .creationUser(user)
                .createDate(LocalDateTime.now())
                .status(Status.CREATED)
                .priority(Priority.MEDIUM)
                .site(site.orElse(null))
                .issueType(issueType)
//                .equipment(equipmentEntity.orElse(null))
                .build();
        applicationRepository.save(newEntity);
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

    public ResponseEntity<List<ApplicationDTO>> getApplicationsBySingleValues( String siteName, String priority, String issueType, String status,String buildingName){
        try {

//            if(priority==null|| !(priority.equals(Priority.LOW)||priority.equals(Priority.HIGH)||priority.equals(Priority.MEDIUM))){
//                log.error("Incorrect Priority");
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            }
//            if(issueType==null|| !(issueType.equals(IssueType.CLIMATE_CONTROL)||issueType.equals(IssueType.ELECTRICAL)||
//                    issueType.equals(IssueType.EQUIPMENT)||issueType.equals(IssueType.INFRASTRUCTURE))){
//                log.error("Incorrect IssueType");
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            }
//            if(status==null|| !(status.equals(Status.CREATED)||status.equals(Status.REJECTED)||
//                    status.equals(Status.VALIDATED)||status.equals(Status.ASSIGNED)||status.equals(Status.COMPLETED)
//                    ||status.equals(Status.ARCHIVED))){
//                log.error("Incorrect Status");
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            }


            List<ApplicationEntity> q = applicationRepository.findByValues(siteName,
                    priority==null?null:Priority.valueOf(priority),
                    issueType==null?null:IssueType.valueOf(issueType),
                    status==null?null:Status.valueOf(status),
                    buildingName);

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

    public ResponseEntity<ApplicationDTO> updateApplication(ApplicationDTO applicationDTO){
        try{
            ApplicationEntity issue = applicationRepository.findById(applicationDTO.getId()).orElseThrow(()->
                {new EntityNotFoundException("Application not found"); return null;});

            issue.setDueDate(applicationDTO.getDueDate());
            issue.setPriority(Priority.valueOf(applicationDTO.getPriority()));
            issue.setDescription(applicationDTO.getDescription());
            issue.setTitle(applicationDTO.getTitle());
            issue.setIssueType(IssueType.valueOf(applicationDTO.getIssueType()));
            Status newStatus=Status.valueOf(applicationDTO.getStatus());
            String result =changeStatusCheck(issue.getStatus(),newStatus);
            if(result!=null){
                log.error(result);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            issue.setStatus(newStatus);
            if(applicationDTO.getAssigneeTechId()!=null){
                Optional<UserEntity> newAssignee =userRepository.findById(applicationDTO.getAssigneeTechId());
                if(newAssignee.isPresent())
                    issue.setAssigneeTech(newAssignee.get());
                else {
                    log.error("Assignee not found in Database");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }else
                issue.setAssigneeTech(null);
            applicationRepository.save(issue);
            return ResponseEntity.ok(applicationDTO);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }
    public ResponseEntity<String> completeApplication(String id){
        ApplicationEntity issue = applicationRepository.findById(id).orElseThrow(()->
            new EntityNotFoundException("Application not found")
        );
        String result=changeStatusCheck(issue.getStatus(), Status.COMPLETED);
        if(result!=null) {
            log.error(result);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
        issue.setStatus(Status.COMPLETED);
        applicationRepository.save(issue);
        return ResponseEntity.ok("Completed");
    }

    public ResponseEntity<Map<String,List<String>>> getStaticData(){
        Map<String,List<String>> ret=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        ret.put("IssueTypes",mapper.convertValue(IssueType.values(),List.class));
        ret.put("Status",mapper.convertValue(Status.values(),List.class));
        ret.put("Priotity",mapper.convertValue(Priority.values(), List.class));
        return ResponseEntity.ok(ret);

    }

    private String changeStatusCheck(Status current, Status status){
        switch (current) {
            case COMPLETED -> {
                if (status == Status.REJECTED || status == Status.CREATED)
                    return ("Invalid status change: Can't go back to creation or rejection" +
                            "from Completed");
            }
            case ARCHIVED, REJECTED ->
            {
                return ("Invalid status change: Can't change status from Rejected or Archived");
            }
            default -> {
                return null;
            }
        }
        return null;
    }

    public List<ApplicationDTO> toDTO(List<ApplicationEntity> entities){

        return entities.stream().map(ApplicationMapper.INSTANCE::toDTO).toList();
    }
}