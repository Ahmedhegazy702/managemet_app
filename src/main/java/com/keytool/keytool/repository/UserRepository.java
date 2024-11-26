package com.keytool.keytool.repository;

import com.keytool.keytool.entity.User;
import com.keytool.keytool.entity.Vacation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

}
