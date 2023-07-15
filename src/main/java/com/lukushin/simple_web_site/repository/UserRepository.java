package com.lukushin.simple_web_site.repository;

import com.lukushin.simple_web_site.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUserName(String name);
}
