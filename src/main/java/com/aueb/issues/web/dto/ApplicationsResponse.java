package com.aueb.issues.web.dto;

import lombok.*;

/**
 * @author George Karampelas
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationsResponse {
    private String name;//todo change it with array of objects with issues
}
