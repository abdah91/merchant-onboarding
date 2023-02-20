package com.merchant.controller;

import com.merchant.controller.advice.RestException;
import com.merchant.service.EmailService;
import com.merchant.service.impl.GeneralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@Slf4j
@RestController
@RequestMapping("/general")
public class GeneralController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private GeneralService generalService;

    @GetMapping("/verifyOtp")
    public ResponseEntity<String> verifyMerchantOtp(@RequestParam String email, @RequestParam String otp ) throws UnknownHostException {
        ResponseEntity<String> responseDTO;
        try {
            responseDTO = new ResponseEntity<>(generalService.verifyMerchantOtp(email, otp), HttpStatus.OK);
        } catch(IllegalArgumentException ex) {
            log.error("Failed to Validate OTP Exception -> {}",ex.getLocalizedMessage());
            throw new RestException(HttpStatus.UNPROCESSABLE_ENTITY,ex.getLocalizedMessage());
        }
        log.info("Merchant record created successfully.");
        return responseDTO;
    }




}
