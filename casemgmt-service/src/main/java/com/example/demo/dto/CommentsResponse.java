package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentsResponse {
    private String commentsId;
    private String caseId;
    private String empId;
    private String commentsDetails;
}
