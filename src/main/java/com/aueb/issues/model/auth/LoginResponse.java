package com.aueb.issues.model.auth;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @author George Karampelas
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String email;
    private String accessToken;
    private String message;

}
