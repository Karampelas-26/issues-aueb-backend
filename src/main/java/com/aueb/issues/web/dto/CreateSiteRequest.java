package com.aueb.issues.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateSiteRequest {
    private String name;
    private String floor;
}