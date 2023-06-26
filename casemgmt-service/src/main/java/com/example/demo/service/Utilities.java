package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Utilities {

    public static String generateId(String module)
    {

        UUID uuid = UUID.randomUUID();
        String caseId = uuid.toString().toUpperCase().replaceAll("-", "");
        return module + caseId.substring(0, 8); // Using first 8 characters of UUID
    }
}
