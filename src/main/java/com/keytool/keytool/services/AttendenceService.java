package com.keytool.keytool.services;

import com.keytool.keytool.entity.Attendence;
import com.keytool.keytool.entity.User;
import com.keytool.keytool.repository.AttendenceRepository;
import com.keytool.keytool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Service
public class AttendenceService {
    @Autowired
    private AttendenceRepository attendenceRepository;

    @Autowired
    private UserRepository userRepository;

    public String recordAttendenc(String uerEmail) {
        User user = userRepository.findByEmail(uerEmail);
        LocalDate today = LocalDate.now();
        Optional<Attendence> exitingAttendence = attendenceRepository.findByUserAndDate(user, today);

        if (exitingAttendence.isPresent()) {
            Attendence attendence = exitingAttendence.get();
            if (attendence.getCheckOutTime() == null) {
                attendence.setCheckOutTime(LocalTime.now());
                attendenceRepository.save(attendence);
                return "checkout succssfuly for user" + uerEmail;

            } else {
                return "User has already checked out";
            }
        } else {

            Attendence attendence = new Attendence();
            attendence.setUser(user);
            attendence.setDate(today);

            attendence.setCheckInTime(LocalTime.now());

            attendenceRepository.save(attendence);
            return "check in record successfully for user" + uerEmail;
        }

    }
    public List<Attendence>getAttendenceforEmployee(Long userID){
        return attendenceRepository.findByUserId(userID);
    }

    public List<Attendence> getAttendanceByEmployeeAndDate(Long userId, LocalDate startDate, LocalDate endDate) {
        return attendenceRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

}




