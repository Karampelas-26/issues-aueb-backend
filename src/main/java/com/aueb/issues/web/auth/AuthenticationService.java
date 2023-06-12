package com.aueb.issues.web.auth;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.ActivationToken;
import com.aueb.issues.model.entity.OTP;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.repository.ActivationTokenRepository;
import com.aueb.issues.repository.OTPRepository;
import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.security.JwtTokenUtil;
import com.aueb.issues.model.auth.LoginRequest;
import com.aueb.issues.model.auth.LoginResponse;
import com.aueb.issues.web.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

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
    private final EmailService mailSender;
    private final OTPRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivationTokenRepository activationTokenRepository;

    private static final int EXPIRATION_TIME_OTP = 15;

    public ResponseEntity<LoginResponse> login(LoginRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());

            String jwtToken = "";

            if(user.isPresent()) {
                jwtToken = jwtTokenUtil.generateAccessToken(user.get());

                return ResponseEntity.ok(
                    LoginResponse.builder()
                    .email(request.getEmail())
                    .accessToken(jwtToken)
                    .build()
                );
            }
            return ResponseEntity.notFound().build();
        }
        catch (DisabledException disabledException) {
            String errorMessage = "Your account is not activated.";
            LoginResponse res = LoginResponse.builder()
                    .message(errorMessage)
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        } catch (BadCredentialsException e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse().builder().message("Invalid credentials").build());
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        ForgotPasswordResponse response = new ForgotPasswordResponse();
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> {
            response.setMessage("User not found");
            return new EntityNotFoundException("User not found");
        });
        String otPassword = generateOTP();

        String body = "Hi " +
                user.getFirstname() + "\n" +
                "Reset your password with One-Time Password: " +
                otPassword;

        mailSender.sendEmail(request.getEmail(), "Reset password", body);
        response.setMessage("Email with OTP send successfully");
        OTP otp = OTP.builder()
                .userId(user.getId())
                .otpPassword(passwordEncoder.encode(otPassword))
                .otpCreationDateTime(LocalDateTime.now())
                .expirationMinutes(EXPIRATION_TIME_OTP)
                .build();
        otpRepository.save(otp);
        return response;
    }

    private String generateOTP(){
        Random random = new Random();
        int otp = 100_000 + random.nextInt(900_000);
        return String.valueOf(otp);
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        ResetPasswordResponse response = new ResetPasswordResponse();
        boolean validOTP = false;

        OTP otp = otpRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("OTP not found"));
        validOTP = isValidOTP(otp, request.getOtp());
        if(validOTP) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            otpRepository.delete(otp);
            response.setSuccess(true);
            response.setMessage("Successfully reset password");
            return response;
        }
        response.setSuccess(false);
        response.setMessage("Failed to reset password");
        return response;
    }

    private boolean isValidOTP(OTP otp, String oneTimePassword) {
        LocalDateTime expirationDate = otp.getOtpCreationDateTime().plus(EXPIRATION_TIME_OTP, ChronoUnit.MINUTES);
        LocalDateTime currentTime = LocalDateTime.now();
        boolean isExpired = currentTime.isAfter(expirationDate);
        boolean validOTP = passwordEncoder.matches(oneTimePassword, otp.getOtpPassword());
        return (!isExpired) && validOTP;
    }

    public ResponseEntity<ResponseMessageDTO> activateUser(ActivationUserRequest request) {

        ActivationToken token = activationTokenRepository.findByActivationToken(request.getActivationToken()).orElseThrow(() -> new EntityNotFoundException("Activation token not found"));

        log.info("activation token: " + token.getActivationToken());

        UserEntity user = userRepository.findById(token.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if(request.getActivationToken().equals(token.getActivationToken())) {
            user.setActivated(true);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            activationTokenRepository.delete(token);
            log.info("success");
            return ResponseEntity.ok(new ResponseMessageDTO("Successfully activate acount"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDTO("Could not activate account, try again"));

    }
}
