package com.mav.CustomerService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "CUSTOMER")
@Entity
public class Customer implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;
    /**
     * Customer: Represents an individual or entity who holds an account or has a relationship with the bank.
     * but This entity Represents an individual entity only and returning customer ID.
     */
    @Id
    @Column(name = "customer_id", length = 100)
    private String customer_id;

    @Column(name = "first_Name")
    private String first_Name;
    @Column(name = "last_Name")
    private String last_Name;
    @Column(name = "date_Of_Birth")
    private Date date_Of_Birth;
    @Column(name = "phone_number")
    private long phone_Number;
    @Column(name = "email")
    private String email;
    @Column(name = "pan_Number")
    private String pan_Number;
    @Column(name = "street_name")
    private String aadhaar;
    @Column(name = "aadhaar")
    private String street_Name;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip_code")
    private String zip_Code;
    @Column(name = "country")
    private String country;
    @Column(name = "create_dDate")
    private Date created_Date;
    @Column(name = "updated_By")
    private String updated_By;

}

