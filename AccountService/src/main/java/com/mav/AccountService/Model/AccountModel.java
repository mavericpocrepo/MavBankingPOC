package com.mav.AccountService.Model;

import lombok.*;
import sun.management.counter.LongArrayCounter;
import sun.security.util.Password;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString

public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="account_Id")
    private Integer account_Id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name="account_Number")
    private Long account_Number;

    @Column(name="customer_id")
    private String customer_id;

    @Column(name="account_Type")
    private String account_Type;

    @Column(name="balance")
    private Long balance;

    @Column(name="currency")
    private String currency;

    @Column(name="status")
    private String status;

    @Column(name="interest_Rate")
    private Long interest_Rate;

    @Column(name="overdraft_Limit")
    private Long overdraft_Limit;
}
