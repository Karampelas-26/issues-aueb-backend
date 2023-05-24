package com.aueb.issues.web;

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
    public ResponseEntity<SubmitApplicationResponse> submitApplication(SubmitApplicationDTO requestDTO){
        //TODO: Valdidate request and create new entity
        return ResponseEntity.ok(new SubmitApplicationResponse(LocalDateTime.now(),"OK"));
    }
}
