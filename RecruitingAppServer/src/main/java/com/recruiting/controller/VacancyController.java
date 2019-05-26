package com.recruiting.controller;

import com.recruiting.Service.UserService;
import com.recruiting.domain.User;
import com.recruiting.domain.Vacancy;
import com.recruiting.repo.VacancyRepo;
import com.recruiting.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/api/recruiter")
public class VacancyController {

    private final VacancyRepo vacancyRepo;

    private final UserRepo userRepo;

    private final UserService userService;

    @Autowired
    public VacancyController(VacancyRepo vacancyRepo, UserRepo userRepo, UserService userService) {
        this.vacancyRepo = vacancyRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_RECRUITER'))")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vacancy> createVacancy(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Vacancy vacancy){
        log.info("Vacancy creating" + vacancy);
        vacancy.setAuthor(userRepo.findByUsername(userDetails.getUsername()));
        return new ResponseEntity<>(vacancyRepo.save(vacancy), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vacancy>> vacancyList(){
        log.info("get all vacancies");
        if (vacancyRepo.findAll() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vacancyRepo.findAll(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_RECRUITER'))")
    @GetMapping("/all/author")
    public ResponseEntity<List<Vacancy>> vacancyListByAuthor(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findByUsername(userDetails.getUsername());
        if (vacancyRepo.findAllByAuthor(user) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vacancyRepo.findAllByAuthor(user), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public  ResponseEntity<Vacancy> getVacancy(@PathVariable("id") Vacancy vacancy){
        log.info("GET");
        log.info(vacancy.toString() + " successfully get from DB");
        return new ResponseEntity<>(vacancy, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_RECRUITER'))")
    @PutMapping("{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable ("id") Vacancy vacancyFromDB, @RequestBody Vacancy vacancy){
        BeanUtils.copyProperties(vacancy, vacancyFromDB,"id","author","createdAt");
        return new ResponseEntity<>(vacancyRepo.save(vacancyFromDB), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_RECRUITER'))")
    @DeleteMapping("{id}")
    public void deleteVacancy(@PathVariable("id") Vacancy vacancy)
    {   log.info("DELETE");
        vacancyRepo.delete(vacancy);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_USER'))")
    @GetMapping("/author/{id}")
    public ResponseEntity<Long> getAuthorId(@PathVariable("id") Vacancy vacancy){
        log.info("author GET");
        return new ResponseEntity<>(vacancy.getAuthor().getId(),HttpStatus.OK);
    }
}
