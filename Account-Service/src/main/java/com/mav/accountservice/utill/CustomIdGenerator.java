package com.mav.accountservice.utill;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        long timestamp = System.currentTimeMillis(); // Get the current timestamp
        int randomNumber = new Random().nextInt(9000) + 1000; // Generate a random 4-digit number
        String customId = String.valueOf(timestamp) + String.valueOf(randomNumber); // Combine the timestamp and random number
        return Long.parseLong(customId); // Return the custom ID as a Long value
    }
}