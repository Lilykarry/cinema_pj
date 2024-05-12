package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,String> {
    List<Users> findByName(String name);

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findUsersByEmail(@Param("email") String email);
    Users findByEmail(String email);

}
