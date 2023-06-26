package com.example.demo.controller;

import com.example.demo.dto.CaseCreateRequest;
import com.example.demo.dto.CaseCreateResponse;
import com.example.demo.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/case")
@RequiredArgsConstructor
public class CaseController {
    private final CaseService service;
    @PostMapping("/createCase")
    public ResponseEntity<?> createCase(@RequestBody CaseCreateRequest request)
    {
        try
        {
            CaseCreateResponse response = service.createCase(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/updateCase/{id}")
    public ResponseEntity<CaseCreateResponse> updateCase(@PathVariable String id, @RequestBody Map<String,Object> fields)
    {
        try
        {
            CaseCreateResponse response = service.updateCase(id, fields);
            return ResponseEntity.ok(response);
        }
        catch (NoSuchElementException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteCase/{id}")
    public ResponseEntity<?> deleteCase(@PathVariable String id)
    {
        try
        {
            service.deleteCase(id);
            return ResponseEntity.ok().body("Case Deleted Successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Case Found");
        }

    }

    @GetMapping("/fetchAllCases")
    public ResponseEntity<List<CaseCreateResponse>> fetchAllCases()
    {
        return ResponseEntity.ok(service.fetchAllCases());
    }

    @GetMapping("/fetchCaseById/{id}")
    public ResponseEntity<CaseCreateResponse> fetchCaseById(@PathVariable String id)
    {
        try
        {
            return ResponseEntity.ok(service.fetchCaseById(id));
        }
        catch (NoSuchElementException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
