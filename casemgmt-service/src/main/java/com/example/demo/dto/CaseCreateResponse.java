package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class CaseCreateResponse {
    @ApiModelProperty(value = "Case ID", example = "SR12345678")
    private String caseId;
    private String customerId;
    private String empId;
    private String customerName;
    private String mobileNum;
    private String caseDetails;
    private String status;
    private String priority;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime resolvedDate;
    private String resolutionDetails;
    private String additionalNotes;

}
