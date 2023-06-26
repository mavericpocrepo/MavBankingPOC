package com.mav.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "ACCOUNT")
@Entity
public class Account implements Serializable {
    private static final long serialVersionUID = -443911446941799411L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", strategy = "com.mav.accountservice.utill.CustomIdGenerator")

    @Column(name = "account_Id")
    private Long account_Id;

    @Column(name = "account_Type")
    private String account_Type;

    @Column(name = "customer_Id")
    private String customer_Id;

    @Column(name = "pan_Number")
    private String pan_Number;

    @Column(name = "balance")
    private String balance;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private String status;

    @Column(name = "interest_Rate")
    private String interest_Rate;

    @Column(name = "overdraft_Limit")
    private String overdraft_Limit;

    @Column(name = "create_dDate")
    private Date created_Date;

    @Column(name = "updated_By")
    private String updated_By;


}
