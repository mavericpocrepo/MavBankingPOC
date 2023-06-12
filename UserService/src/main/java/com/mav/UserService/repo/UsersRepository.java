package com.mav.UserService.repo;

import com.mav.UserService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);
}
