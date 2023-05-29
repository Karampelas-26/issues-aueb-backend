package com.aueb.issues.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class ApplicationResponse {
    private LocalDateTime timestamp;
    private String responseDescription;
}
