package com.merchant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDto {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Date createdDate;
    private String status;
    private String identificationNumber;
    private String licenseNumber;
    private String identificationStatus;
    private String licenseStatus;
    private String otp;

}
