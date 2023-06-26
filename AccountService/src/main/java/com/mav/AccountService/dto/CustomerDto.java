package com.mav.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

    private long id;
    private String customer_id;
    private String first_Name;
    private String last_Name;
    private Date date_Of_Birth;
    private long phone_number;
    private String email;
    private String pan_Number;
    private String aadhaar;
    private String street_name;
    private String city;
    private String state;
    private String zip_code;
    private String country;

}
