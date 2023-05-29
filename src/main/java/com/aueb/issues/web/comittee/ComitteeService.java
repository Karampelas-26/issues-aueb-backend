package com.aueb.issues.web.comittee;

import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import org.springframework.stereotype.Service;

/**
 * @author George Karampelas
 */
@Service
public class ComitteeService {


    public TeacherApplicationsDTO getApplications() {
        return TeacherApplicationsDTO.builder()
                .name("i m committekghle")
                .build();
    }
}
