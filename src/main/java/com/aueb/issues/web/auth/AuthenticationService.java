package com.aueb.issues.web.auth;

import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.security.JwtTokenUtil;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.auth.LoginRequest;
import com.aueb.issues.model.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

            Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());

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
