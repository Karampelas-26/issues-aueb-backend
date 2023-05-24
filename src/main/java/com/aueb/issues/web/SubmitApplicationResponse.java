package com.aueb.issues.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class SubmitApplicationResponse {
    private LocalDateTime timestamp;
    private String responseDescription;
}
