package com.aueb.issues.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum IssueType {
    ELECTRICAL,
    EQUIPMENT,
    CLIMATE_CONTROL,
    INFRASTRUCTURE;

    public List<String> getAll(){
        List<String> ret=new ArrayList<>();
        ret.add(ELECTRICAL.name());
        ret.add(EQUIPMENT.name());
        ret.add(CLIMATE_CONTROL.name());
        ret.add(INFRASTRUCTURE.name());
        return ret;
    }
}
