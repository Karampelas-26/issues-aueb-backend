package com.aueb.issues.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class OuterStatPojo {
    private List<String> labels;
    private List<InnerStatPojo> innerStatPojos;
}
