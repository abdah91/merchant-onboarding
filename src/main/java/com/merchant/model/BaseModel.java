package com.merchant.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class BaseModel {

    private boolean isDeleted;

    private LocalDateTime createdAt;
    private Integer createdBy;
    private LocalDateTime updatedAt;
    private Integer updatedBy;
    private String userAgent;
    private Integer userAgentVersion;
}
