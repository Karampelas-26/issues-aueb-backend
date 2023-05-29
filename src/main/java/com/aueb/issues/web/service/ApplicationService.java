package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.services.BuildingService;
import com.aueb.issues.model.services.SiteService;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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

    public ResponseEntity<List<ApplicationDTO>> getApplications(){
        List<ApplicationDTO> ret = new ArrayList<>();
        try{
            List<ApplicationEntity> issues = applicationRepository.findAll();
            ObjectMapper mapper = new ObjectMapper();
            for (ApplicationEntity issue: issues){
                mapper.convertValue(issue,ApplicationDTO.class);
            }
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return (ResponseEntity<List<ApplicationDTO>>) ret;
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

            ApplicationEntity issue = applicationRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Application not found"));
            if(issue!=null) {
                return ResponseEntity.badRequest().body("No issue with such id");
            }
            if(request.getTitle()!=null)
                issue.setTitle(request.getTitle());
            if(request.getPriority()!=null)
                issue.setPriority(request.getPriority());
            if(request.getSiteId()!=null)
                issue.setSite(siteService.getSiteBySiteId(request.getSiteId()));
            if(request.getBuildingId()!=0)
                issue.setBuilding(buildingService.getBuildingById((request.getBuildingId())));
            if(request.getDueDate()!=null)
                issue.setCompletionDate(request.getDueDate());
            return ResponseEntity.ok(null);

        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }
}