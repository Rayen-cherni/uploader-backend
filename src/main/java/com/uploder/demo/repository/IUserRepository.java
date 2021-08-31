package com.uploder.demo.repository;

import com.uploder.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
