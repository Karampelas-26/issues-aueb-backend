package com.aueb.issues.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author George Karampelas
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDTO {
    private String message;
}
