package com.aueb.issues.web.comittee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author George Karampelas
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private char gender;
    private String address;
    private String role;
}
