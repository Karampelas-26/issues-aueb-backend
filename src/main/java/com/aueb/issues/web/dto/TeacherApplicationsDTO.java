package com.aueb.issues.web.dto;

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
    private String name;//todo change it with array of objects with issues
}
