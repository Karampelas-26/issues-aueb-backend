package com.aueb.issues.model.technician;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.entity.BuildingEntity;
import com.aueb.issues.model.entity.Priority;
import com.aueb.issues.model.entity.SitesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private List<ApplicationEntity> issuesList;
    //private Optional<ApplicationEntity> issue;
}
