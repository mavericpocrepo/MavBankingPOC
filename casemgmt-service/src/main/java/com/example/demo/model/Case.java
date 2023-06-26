package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="caseData")
public class Case {
    @Id
    private String caseId;
    private String customerId;
    private String empId;
    private String customerName;
    private String mobileNum;
    private String caseDetails;
    private String status;
    private int priority;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime resolvedDate;
    private String resolutionDetails;
    private String additionalNotes;

    @PreUpdate
    public void preUpdate()
    {
        this.lastUpdatedDate=LocalDateTime.now();
    }

}
