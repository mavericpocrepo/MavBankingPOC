package com.example.demo.service;

import com.example.demo.clients.ServiceClients;
import com.example.demo.dto.CommentsCreateRequest;
import com.example.demo.dto.CommentsResponse;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.model.Comments;
import com.example.demo.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository repository;
    private final ServiceClients clients;
    public CommentsResponse createComment(String caseId, CommentsCreateRequest request) {
        EmployeeResponse e  =clients.getUserByEmail(1l);
        System.out.println(e);
        Comments comment = Comments.builder()
                .commentId(Utilities.generateId("CM"))
                .caseId(request.getCaseId())
                .empId(request.getEmpId())
                .commentDetails(request.getCommentsDetails())
                .build();
        Comments savedComment = repository.save(comment);
        CommentsResponse response = CommentsResponse.builder()
                .commentsId(savedComment.getCommentId())
                .caseId(savedComment.getCaseId())
                .empId(savedComment.getEmpId())
                .commentsDetails(savedComment.getCommentDetails())
                .build();
        return response;

    }
}
