package com.aueb.issues.auth;

import entity.auth.LoginRequest;
import entity.auth.LoginResponse;
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
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }


    //just a GET for testing
    @GetMapping
    public ResponseEntity<String> sayHi(){
        return ResponseEntity.ok("hello dude");
    }

}
