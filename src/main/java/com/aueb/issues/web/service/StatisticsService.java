package com.aueb.issues.web.service;

import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.repository.ApplicationRepository;
import jakarta.persistence.TemporalType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StatisticsService {
    @Autowired
    ApplicationRepository applicationRepository;
    public ResponseEntity<List<Map<String, Object>>> getAllStatsByMonth(Long buildingId, String issueType, LocalDateTime creationStart, LocalDateTime creationEnd){

        if(issueType!=null  && ! (issueType.equals(IssueType.CLIMATE_CONTROL.name())||issueType.equals(IssueType.ELECTRICAL.name())||
                issueType.equals(IssueType.EQUIPMENT.name())||issueType.equals(IssueType.INFRASTRUCTURE.name()))){
            log.error("Incorrect IssueType");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(applicationRepository.countApplicationsByMonthWithFilters(creationStart,creationEnd,buildingId,issueType), HttpStatus.OK);
    }
}
