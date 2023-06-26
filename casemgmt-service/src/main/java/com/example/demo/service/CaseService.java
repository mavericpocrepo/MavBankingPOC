package com.example.demo.service;

import com.example.demo.dto.CaseCreateRequest;
import com.example.demo.dto.CaseCreateResponse;
import com.example.demo.model.Case;
import com.example.demo.repository.CaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaseService {
    private final CaseRepository repository;

    public CaseCreateResponse createCase(CaseCreateRequest request)
    {
        Case obj = Case.builder()
                .caseId(Utilities.generateId("SR"))
                .customerName(request.getCustomerName())
                .mobileNum(request.getMobileNum())
                .caseDetails(request.getCaseDetails())
                .priority(1)
                .status("Open")
                .creationDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .build();
            repository.save(obj);

        CaseCreateResponse response = CaseCreateResponse.builder()
                .caseId(obj.getCaseId())
                .status(obj.getStatus())
                .build();
        return response;
    }



    public CaseCreateResponse updateCase(String id, Map<String, Object> fields) {

            Optional<Case> existingCase = repository.findById(id);
            if (existingCase.isPresent()) {
                fields.forEach((key, value) -> {
                    Field field = ReflectionUtils.findField(Case.class, key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, existingCase.get(), value);
                });
            }
            Case obj = repository.save(existingCase.get());
            CaseCreateResponse response = CaseCreateResponse.builder()
                    .caseId(obj.getCaseId())
                    .customerName(obj.getCustomerName())
                    .status(obj.getStatus())
                    .build();
            return response;


    }
    public void deleteCase(String id)
    {
        repository.deleteById(id);
    }

    public List<CaseCreateResponse> fetchAllCases() {
        List<Case> cases =  repository.findAll();
        List<CaseCreateResponse> responses = cases.stream()
                .map(this::mapCaseToResponse)
                .collect(Collectors.toList());
        return responses;

    }
    private CaseCreateResponse mapCaseToResponse(Case caseObj) {
        return CaseCreateResponse.builder()
                .caseId(caseObj.getCaseId())
                .customerName(caseObj.getCustomerName())
                .mobileNum(caseObj.getMobileNum())
                // Map other properties as needed
                .build();
    }

    public CaseCreateResponse fetchCaseById(String id)
    {
        Optional<Case> caseObj = repository.findById(id);
        CaseCreateResponse response = CaseCreateResponse.builder()
                .caseId(caseObj.get().getCaseId())
                .customerName(caseObj.get().getCustomerName())
                .mobileNum(caseObj.get().getMobileNum())
                .build();
        return response;
    }
}
