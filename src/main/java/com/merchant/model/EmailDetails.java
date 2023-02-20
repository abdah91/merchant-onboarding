package com.merchant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Class
public class EmailDetails {

    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    public EmailDetails(String email, String body, String subject) {
        this.recipient = email;
        this.msgBody = body;
        this.subject = subject;
    }
}