package com.example.demo.service;

import com.example.demo.model.Admins;
import com.example.demo.model.Users;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users userEntity = userRepository.findByEmail(email);
        Admins adminEntity = adminRepository.findAdminsByEmail(email);

        if (userEntity == null && adminEntity == null) {
            throw new UsernameNotFoundException("Không tìm thấy user hoặc admin với email: " + email);
        }
        UserPrincipal userPrincipal = new UserPrincipal();
        // If user entity is found or admin is found
        if (userEntity != null ) {
            userPrincipal.setEmail(email);
            userPrincipal.setPassword(userEntity.getPassword());
        } else if(adminEntity != null){
            userPrincipal.setEmail(email);
            userPrincipal.setPassword(adminEntity.getPassword());
        }
        return userPrincipal;
    }


    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("1234"));
    }
}
