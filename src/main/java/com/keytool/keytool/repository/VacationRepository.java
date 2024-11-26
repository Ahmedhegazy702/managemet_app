package com.keytool.keytool.repository;

import com.keytool.keytool.entity.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation,Long> {
    Page<Vacation>findByUsername(String name,Pageable pageable);
    Page<Vacation> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

}
