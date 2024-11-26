package com.keytool.keytool.controllers;

import com.keytool.keytool.entity.User;
import com.keytool.keytool.entity.Vacation;
import com.keytool.keytool.services.UserService;
import com.keytool.keytool.services.VacationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vacation")
public class VacationController {
    @Autowired
    private VacationService vacationService;

    @Autowired
    private UserService userService ;

    @PostMapping("/request")
    public ResponseEntity<Vacation>requestVacation(@RequestBody Vacation vacation,Authentication authentication){
        String loggedInEmail = authentication.getName();

        User user = userService.findUserByEmail(loggedInEmail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        vacation.setUser(user);
        Vacation savedVacation = vacationService.requestvacation(vacation);


        return ResponseEntity.status(HttpStatus.CREATED).body(savedVacation);
    }


@GetMapping("/vacation-requests")
public ResponseEntity<Page<Vacation>> getUserVacationRequests(
        Authentication authentication,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {



    String email = authentication.getName();
    boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

    Page<Vacation> vacationRequests;

    if (isAdmin)
    {
        vacationRequests = vacationService.getAllVacationRequests(page, size, startDate, endDate);
    }

     else {
        Pageable pageable= PageRequest.of(page,size, Sort.by("startDate"));
        vacationRequests = vacationService.getUserVacationRequest(email,pageable);
    }

    return ResponseEntity.ok(vacationRequests);
}

    @PostMapping("/change")

    public ResponseEntity<Vacation> changeRequestStatus(@RequestParam Long requestId,
                                                               @RequestParam String status,
                                                        Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Vacation updatedRequest = vacationService.changeRequestStatus(requestId, status);

        if (updatedRequest != null) {
            return ResponseEntity.ok(updatedRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteVacation(@PathVariable Long id) {
        vacationService.deleteVacation(id);
    }

}
