package com.aueb.issues.model.auth;

import lombok.*;

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
}
