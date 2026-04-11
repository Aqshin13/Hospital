package com.hospital.controller;


import com.hospital.dto.request.Credentials;
import com.hospital.dto.request.UserCreateRequest;
import com.hospital.dto.response.AuthResponse;
import com.hospital.dto.response.RefreshTokenResponse;
import com.hospital.service.AuthService;
import com.hospital.service.OtpService;
import com.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final OtpService otpService;
    private final AuthService authService;

//    Hekimi admin birbasa register edir ve active olur
    @PostMapping("/doctor/register")
    public ResponseEntity<?> registerDoctor(@RequestBody UserCreateRequest doctorCreateRequest) {
        userService.saveDoctorUser(doctorCreateRequest);
        return ResponseEntity.ok("Doctor is created");
    }


//    Pasiyent evvelce register olur sonra ona mail gedir o url ile active edir ozunu
    @PostMapping("/patient/register")
    public ResponseEntity<?> registerPatient(@RequestBody UserCreateRequest patientCreateRequest) {
        userService.savePatientUser(patientCreateRequest);
        return ResponseEntity.ok("Check your mailbox");
    }



//    active olan istifadeciler login olmaq ucun otp teleb edir sonra otpni girip login olacaqlar
    @PostMapping("/send-otp/{email}")
    public ResponseEntity<?> sendOtpToLogin(@PathVariable String email) {
        otpService.sendOtp(email);
        return ResponseEntity.ok("Check your mailbox");
    }

// otp ve mail duzgundurse onda jwt token qaytar...
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Credentials credentials) {
        return ResponseEntity.ok(authService.login(credentials));
    }

//Registrasiya tamamlanmasi maile url gedecek o url click edildiyine hesap active olacaq ve login olacaq
    @PatchMapping("/activate/{activationToken}")
    public ResponseEntity<AuthResponse> activate(@PathVariable String activationToken){
        return ResponseEntity.ok(authService.activateUser(activationToken));
    }


//    Tokeni bununla refresh edecem
    @PostMapping("/refresh-token/{refreshToken}")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@PathVariable String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }


}
