package com.mav.accountservice.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class AccountDto {
    private long id;
    private String account_Type;
    private String customer_id;
    private String pan_Number;
    private String balance;
    private String currency;
    private String status;
    private String interest_Rate;
    private String overdraft_Limit;
    private Date created_Date;
    private String updated_By;

}
