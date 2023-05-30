package com.aueb.issues.web.dto;

import com.aueb.issues.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String password;
    private String phone;
    private String firstname;
    private String lastname;
    private String gender;
    private String address;
    private Date createdDate;
    private Role role;
    private boolean activated;
}
