package com.aueb.issues.web.service;

import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.ApplicationResponse;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ApplicationsService {

    public ResponseEntity<List<TeacherApplicationsDTO>> getTeacherApplications(){
        List<TeacherApplicationsDTO> ret= new ArrayList<>();
        //TODO:RETURN APPLICATIONS BASED ON PREFERENCES
        return ResponseEntity.ok(ret);
    }
    public ResponseEntity<ApplicationResponse> submitApplication(ApplicationDTO requestDTO){
        //TODO: Valdidate request and create new entity
        return ResponseEntity.ok(new ApplicationResponse(LocalDateTime.now(),"OK"));
    }

    public ResponseEntity<List<ApplicationDTO>> getApplications(){
        List<ApplicationDTO> ret=new ArrayList<>();
        return (ResponseEntity<List<ApplicationDTO>>) ret;
    }
}
