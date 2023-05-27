package com.aueb.issues.web.auth;

import com.aueb.issues.email.EmailService;
import com.aueb.issues.model.entity.OTP;
import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.repository.OTPRepository;
import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.security.JwtTokenUtil;
import com.aueb.issues.model.auth.LoginRequest;
import com.aueb.issues.model.auth.LoginResponse;
import com.aueb.issues.web.dto.ForgotPasswordRequest;
import com.aueb.issues.web.dto.ForgotPasswordResponse;
import com.aueb.issues.web.dto.ResetPasswordRequest;
import com.aueb.issues.web.dto.ResetPasswordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    private static final int EXPIRATION_TIME_OTP = 15;

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

    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());
        ForgotPasswordResponse response = new ForgotPasswordResponse();
        if(user.isEmpty()) {
            response.setMessage("User with this email does not exist");
        }
        String otPassword = generateOTP();

        String body = "Hi " +
                user.get().getFirstname() + "\n" +
                "Reset your password with One-Time Password: " +
                otPassword;

        mailSender.sendEmail(request.getEmail(), "Reset password", body);
        response.setMessage("Email with OTP send successfully");
        OTP otp = OTP.builder()
                .userId(user.get().getId())
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
        Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());
        ResetPasswordResponse response = new ResetPasswordResponse();
        boolean validOTP = false;
        if (user.isPresent()){
            Optional<OTP> otp = otpRepository.findByUserId(user.get().getId());
            if (otp.isPresent()){
                validOTP = isValidOTP(otp.get(), request.getOtp());
            }
        }
        if(validOTP) {
            user.get().setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user.get());
//            otpRepository.deleteByUserId(user.get().getId());
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
        log.info("is token expired? " + isExpired);
        boolean validOTP = passwordEncoder.matches(oneTimePassword, otp.getOtpPassword());
        log.info("is valid otp password? " + validOTP);
        return (!isExpired) && validOTP;
    }
}
