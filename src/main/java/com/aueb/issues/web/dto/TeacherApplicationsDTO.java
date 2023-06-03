package com.aueb.issues.web.dto;

import com.aueb.issues.model.enums.Status;
import lombok.*;

/**
 * @author George Karampelas
 *
 * This will transfer data to teacher role. Will contain only the nessecary info.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherApplicationsDTO {
    private String id;
    private String title;
    private Status status;
    private String siteName;
}
