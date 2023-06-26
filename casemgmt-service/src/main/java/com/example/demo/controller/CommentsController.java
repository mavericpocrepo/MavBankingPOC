package com.example.demo.controller;

import com.example.demo.clients.ServiceClients;
import com.example.demo.dto.CommentsCreateRequest;
import com.example.demo.dto.CommentsResponse;
import com.example.demo.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService service;
    @PostMapping("/createComment/{caseId}")
    public ResponseEntity<CommentsResponse> createComment(@PathVariable String caseId, @RequestBody CommentsCreateRequest request)
    {
        try
        {
            return ResponseEntity.ok(service.createComment(caseId,request));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
