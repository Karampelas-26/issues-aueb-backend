package com.aueb.issues.web.comittee;

import com.aueb.issues.web.dto.ApplicationsResponse;
import org.springframework.stereotype.Service;

/**
 * @author George Karampelas
 */
@Service
public class ComitteeService {


    public ApplicationsResponse getApplications() {
        return ApplicationsResponse.builder()
                .name("i m committee")
                .build();
    }
}
