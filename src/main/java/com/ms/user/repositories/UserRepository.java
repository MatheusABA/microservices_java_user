package com.ms.user.repositories;

import com.ms.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {


    boolean existsUserByEmail(String email);
}
