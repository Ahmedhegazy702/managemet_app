package com.keytool.keytool.repository;

import com.keytool.keytool.entity.Attendence;
import com.keytool.keytool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendenceRepository extends JpaRepository<Attendence,Long> {

    Optional<Attendence> findByUserAndDate(User user, LocalDate date);
    List<Attendence> findByUserId(Long userId);
    List<Attendence>findByUserIdAndDateBetween(Long userId, LocalDate startDate,LocalDate endDate);
}
