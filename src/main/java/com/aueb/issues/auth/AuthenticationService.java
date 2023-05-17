package com.aueb.issues.auth;

import com.aueb.issues.security.JwtTokenUtil;
import com.aueb.issues.user.User;
import com.aueb.issues.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author George Karampelas
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public LoginResponse login(LoginRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            Optional<User> user = userRepository.findByEmail(request.getEmail());

            String jwtToken = "";

            if(user.isPresent()) jwtToken = jwtTokenUtil.generateAccessToken(user.get());

            return LoginResponse.builder()
                    .email(request.getEmail())
                    .accessToken(jwtToken)
                    .build();
        }catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }
}
