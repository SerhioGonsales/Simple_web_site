package com.lukushin.simple_web_site.repository;

import com.lukushin.simple_web_site.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByActivationCode(String code);
}
