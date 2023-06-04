package com.aueb.issues.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Karampelas
 */
public enum Status {
    CREATED,
    REJECTED,
    VALIDATED,
    ASSIGNED,
    COMPLETED,
    ARCHIVED;

    public List<String> getAll(){
        List<String> ret=new ArrayList<>();
        ret.add(COMPLETED.name());
        ret.add(CREATED.name());
        ret.add(REJECTED.name());
        ret.add(VALIDATED.name());
        ret.add(ASSIGNED.name());
        ret.add(ARCHIVED.name());
        return ret;
    }
}
