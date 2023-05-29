package com.aueb.issues.repository.representations;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRepresentation {
    @CsvBindByName(column = "firstname")
    private String fName;
    @CsvBindByName(column = "lastname")
    private String lName;
    @CsvBindByName(column = "email")
    private String email;
    @CsvBindByName(column = "phone")
    private String phone;
    @CsvBindByName(column = "address")
    private String address;
    @CsvBindByName(column = "gender")
    private String gender;
    @CsvBindByName(column = "role")
    private String role;
    @CsvBindByName(column = "team")
    private String technicalTeam;
}
