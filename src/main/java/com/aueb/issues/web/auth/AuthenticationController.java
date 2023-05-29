package com.aueb.issues.web.auth;

import com.aueb.issues.model.auth.LoginRequest;
import com.aueb.issues.model.auth.LoginResponse;
import com.aueb.issues.model.entity.ActivationToken;
import com.aueb.issues.web.dto.ForgotPasswordRequest;
import com.aueb.issues.web.dto.ForgotPasswordResponse;
import com.aueb.issues.web.dto.ResetPasswordRequest;
import com.aueb.issues.web.dto.ResetPasswordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author George Karampelas
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        return ResponseEntity.ok(authenticationService.forgotPassword(forgotPasswordRequest));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(authenticationService.resetPassword(resetPasswordRequest));
    }

    @PostMapping("/activation")
    public ResponseEntity<ActivationUserResponse> activateUser(@RequestBody ActivationUserRequest request){
        return authenticationService.activateUser(request);
    }
}
