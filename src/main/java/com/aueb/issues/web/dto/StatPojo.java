package com.aueb.issues.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class StatPojo {
    private List<String> labels;
    private String label;
    private List<String>data;
}
