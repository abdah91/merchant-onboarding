package com.merchant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Merchant")
public class Merchant {

    @Id
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

    // getters and setters
}