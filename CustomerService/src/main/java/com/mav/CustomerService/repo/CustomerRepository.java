package com.mav.CustomerService.repo;

import com.mav.CustomerService.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.customer_id = :customer_id")
    Customer findByCustomer_id(@Param("customer_id") String customer_id);
    @Modifying
    @Query("DELETE FROM Customer c WHERE c.customer_id = :customer_id")
    void deleteByCustomerId(@Param("customer_id") String customer_id);
    @Query("SELECT c FROM Customer c WHERE c.pan_Number = :pan_Number")
    Customer findByPanNumber(String pan_Number);
}
