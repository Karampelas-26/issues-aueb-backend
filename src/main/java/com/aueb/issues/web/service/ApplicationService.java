package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Status;
import com.aueb.issues.model.mapper.ApplicationMapper;
import com.aueb.issues.model.services.BuildingService;
import com.aueb.issues.model.services.SiteService;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
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
            ret=issues.stream().map(ApplicationMapper.INSTANCE::toDTO).collect(Collectors.toList());
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }

    public ResponseEntity<List<ApplicationDTO>> getApplicationsBySingleValues( String siteName, String priority, String issueType, String status){
        try {
            log.info(siteName);

//            if(priority==null|| !(priority.equals(Priority.LOW)||priority.equals(Priority.HIGH)||priority.equals(Priority.MEDIUM))){
//                log.error("Incorrect Priority");
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            }
//            if(issueType==null|| !(issueType.equals(IssueType.CLIMATE_CONTROL)||issueType.equals(IssueType.ELECTRICAL)||
//                    issueType.equals(IssueType.EQUIPMENT)||issueType.equals(IssueType.INFRASTRUCTURE))){
//                log.error("Incorrect IssueType");
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            }
         //   if(status==null|| !(status.equals(Status.CREATED)||status.equals(Status.REJECTED)||
//                    status.equals(Status.VALIDATED)||status.equals(Status.ASSIGNED)||status.equals(Status.COMPLETED)
//                    ||status.equals(Status.ARCHIVED))){
//                log.error("Incorrect Status");
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            }


            List<ApplicationEntity> q = applicationRepository.findByValues(siteName,
                    priority==null?null:Priority.valueOf(priority),
                    issueType==null?null:IssueType.valueOf(issueType),
                    status==null?null:Status.valueOf(status));
            List<ApplicationDTO> ret = q.stream().map(ApplicationMapper.INSTANCE::toDTO).collect(Collectors.toList());
            return ResponseEntity.ok(ret);
        }
        catch (Exception e){
            log.error(e.toString());
        }
        return ResponseEntity.internalServerError().body(null);
    }

    public ResponseEntity<String> deleteIssue(String id){
        try{
            applicationRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No such entity found"));
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
}