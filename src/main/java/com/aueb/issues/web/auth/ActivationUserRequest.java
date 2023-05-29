package com.aueb.issues.web.auth;

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
public class ActivationUserRequest {
    private String activationToken;
    private String password;
}
