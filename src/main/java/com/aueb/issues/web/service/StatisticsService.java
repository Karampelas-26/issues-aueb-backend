package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.repository.BuildingRepository;
import com.aueb.issues.web.dto.StatPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<StatPojo> getAllStatsByMonth(Long buildingId, String issueType, LocalDateTime creationStart, LocalDateTime creationEnd) {
        // Validate issueType
        if (!isValidIssueType(issueType)) {
            log.error("Incorrect IssueType");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Get building by ID
        BuildingEntity building = getBuildingById(buildingId);

        // Create labels
        LocalDateTime start = creationStart != null ? creationStart : LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = creationEnd != null ? creationEnd : LocalDateTime.now();
        List<String> labels = createLabels(start, end);

        // Populate data based on hash mapping
        List<String> data = populateData(start, end, buildingId, issueType);

        // Create label for name
        String pojoLabel = (building == null ? "All Buildings" : building.getName()) + " / "+
                (issueType==null?"Every Type":issueType);
        return new ResponseEntity<>(new StatPojo(labels, pojoLabel, data), HttpStatus.OK);
    }

    private boolean isValidIssueType(String issueType) {
        return issueType == null ||
            Arrays.stream(IssueType.values()).map(Enum::name).toList().contains(issueType);
    }

    private BuildingEntity getBuildingById(Long buildingId) {
        if (buildingId != null) {
            Optional<BuildingEntity> building = buildingRepository.getBuildingById(buildingId);
            return building.orElse(null);
        }
        return null;
    }

    private List<String> createLabels(LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M");
        List<String> labels = new ArrayList<>();
        LocalDateTime current = start;
        while (!current.isAfter(end)) {
            labels.add(current.format(formatter));
            current = current.plusMonths(1);
        }
        return labels;
    }

    private List<String> populateData(LocalDateTime start, LocalDateTime end, Long buildingId, String issueType) {
        List<String> data = new ArrayList<>();
        Map<String, Integer> positions = new HashMap<>();
        int pos = 0;
        LocalDateTime current = start;
        while (!current.isAfter(end)) {
            positions.put(current.format(DateTimeFormatter.ofPattern("yyyy-M")), pos);
            data.add("0");
            current = current.plusMonths(1);
            pos++;
        }
        List<Map<String, Object>> res = applicationRepository.countApplicationsByMonthWithFilters(start, end, buildingId, issueType);
        for (Map<String, Object> re : res) {
            String monthNumber = re.get("monthnumber").toString();
            String count = re.get("count").toString();
            int index = positions.getOrDefault(monthNumber, -1);
            if (index != -1) {
                data.set(index, count);
            }
        }
        return data;
    }

}
