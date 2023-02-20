package com.merchant.service.impl;

import com.merchant.exception.EntityNotFoundException;
import com.merchant.model.EmailDetails;
import com.merchant.model.Merchant;
import com.merchant.model.MerchantHistory;
import com.merchant.repository.MerchantHistoryRepository;
import com.merchant.repository.MerchantRepository;
import com.merchant.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GeneralService {

    @Autowired
    EmailService emailService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantHistoryRepository merchantHistoryRepository;

    public String verifyMerchantOtp(String email, String otp) {
        Merchant merchant = merchantRepository.findByEmail(email);
        if(merchant == null){
            throw  new EntityNotFoundException("Merchant not found");
        }else{
            if(merchant.getOtp().equalsIgnoreCase(otp)){

                if(merchant.getStatus().equalsIgnoreCase("verified")){
                    return "Merchant OTP already verified!";
                }

                merchant.setIdentificationStatus("Pending");
                merchant.setLicenseStatus("Pending");
                merchant.setStatus("verified");
                merchantRepository.save(merchant);

                MerchantHistory merchantHistory = new MerchantHistory();
                merchantHistory.setMerchant(merchant.getId());
                merchantHistory.setDate(new Date());
                merchantHistory.setActivity("Merchant OTP is verified with email:"+ merchant.getEmail());
                merchantHistoryRepository.save(merchantHistory);

                return "Merchant OTP verified Successfully";

            }else{
                return "Invalid OTP, try again";
            }
        }
    }


}
