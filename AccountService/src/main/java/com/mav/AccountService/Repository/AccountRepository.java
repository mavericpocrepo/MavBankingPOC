package com.mav.AccountService.Repository;

import com.mav.AccountService.Model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountModel, Integer>{
}
