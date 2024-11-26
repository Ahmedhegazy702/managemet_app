package com.keytool.keytool.services;

import com.keytool.keytool.entity.User;
import com.keytool.keytool.entity.Vacation;
import com.keytool.keytool.repository.UserRepository;
import com.keytool.keytool.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VacationService {
    @Autowired
   private VacationRepository vacationRepository ;

    @Autowired
    private UserRepository userRepository ;

    public Vacation requestvacation(Vacation vacation){

        vacation.setStatus("PENDING");
        return vacationRepository.save(vacation);
    }

    public Page<Vacation> getUserVacationRequest(String email,Pageable pageable) {
       // return vacationRepository.findByUsername(email);
        return vacationRepository.findByUsername(email,pageable);
    }

    public Page<Vacation> getAllVacationRequests(int page, int size, LocalDate startDate,LocalDate endDate) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("startDate"));
        if(startDate!=null&&endDate!=null){
            return vacationRepository.findByStartDateBetween( startDate,  endDate, pageable);

        }else {
            return vacationRepository.findAll(pageable);
        }
    }

    public Vacation changeRequestStatus(Long requestId, String status) {
        Vacation vacationRequest = vacationRepository.findById(requestId).orElse(null);

        if (vacationRequest != null) {
            vacationRequest.setStatus(status);
            return vacationRepository.save(vacationRequest);
        }

        return null;
    }

    public void deleteVacation(Long id){
        vacationRepository.deleteById(id);
    }
}
