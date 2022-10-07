package com.gmv2.pdv.repository;

import com.gmv2.pdv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
