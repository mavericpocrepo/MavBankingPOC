package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseCreateRequest {
    private String customerName;
    private String mobileNum;
    private String caseDetails;
    private String additionalNotes;

}
