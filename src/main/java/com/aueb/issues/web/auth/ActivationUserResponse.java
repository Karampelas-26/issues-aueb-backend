package com.aueb.issues.web.auth;

import com.aueb.issues.model.entity.UserEntity;
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
public class ActivationUserResponse {
    private UserEntity user;
}
