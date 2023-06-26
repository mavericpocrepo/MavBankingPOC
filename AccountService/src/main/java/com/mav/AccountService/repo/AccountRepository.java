package com.mav.accountservice.repo;

import com.mav.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.customer_Id = :customer_Id")
    List<Account> findAccountByCustomer_id(@Param("customer_Id") String customer_Id);

    @Modifying
    @Query("DELETE FROM Account a WHERE a.account_Id = :account_Id AND a.customer_Id = :customer_Id")
    void deleteAccountByIds(@Param("account_Id") Long account_Id, @Param("customer_Id") String customer_Id);
}
