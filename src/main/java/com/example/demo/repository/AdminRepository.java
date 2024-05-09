package com.example.demo.repository;

import com.example.demo.model.Admins;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admins,Integer> {

    @Query("SELECT a FROM Admins a WHERE a.email = :email")
    Admins findAdminsByEmail(@Param("email") String email);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Admins a WHERE a.email = :email")
    boolean existsAdminByEmail(@Param("email") String email);
    Admins findByEmail(String email);
}
