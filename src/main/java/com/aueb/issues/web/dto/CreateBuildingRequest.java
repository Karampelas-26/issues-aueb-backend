package com.aueb.issues.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateBuildingRequest {
    private String name;
    private String address;
    private int floors;
    private List<CreateSiteRequest> sites;
}
