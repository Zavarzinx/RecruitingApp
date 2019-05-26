package com.recruiting.controller;

import com.recruiting.Service.UserService;
import com.recruiting.domain.Resume;
import com.recruiting.domain.User;
import com.recruiting.repo.ResumeRepo;
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

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@Slf4j
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeRepo resumeRepo;

    private final UserRepo userRepo;

    private final UserService userService;


    @Autowired
    public ResumeController(ResumeRepo resumeRepo, UserRepo userRepo, UserService userService){
        this.resumeRepo = resumeRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resume>> resumesList(){
        if (resumeRepo.findAll() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resumeRepo.findAll(), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public  ResponseEntity<Resume> getResume(@PathVariable("id") Resume resume){
        log.info("GET");
        log.info(resume.toString() + " successfully get from DB");
        return new ResponseEntity<>(resume, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_USER'))")
    @GetMapping("/all/user")
    public ResponseEntity<List<Resume>> getAllResumeByUser(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findByUsername(userDetails.getUsername());
        log.info(resumeRepo.findAllByAuthor(user) + " successfully get from DB");

        if (resumeRepo.findAllByAuthor(user) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resumeRepo.findAllByAuthor(user), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_USER'))")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Resume> createResume(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Resume resume){
        resume.setAuthor(userRepo.findByUsername(userDetails.getUsername()));
        log.info("POST");
        log.info(resume.toString() + " successfully saved into DB");
        return new ResponseEntity<>(resumeRepo.save(resume), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_USER'))")
    @PutMapping("{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable ("id") Resume resumeFromDB, @RequestBody Resume resume){
        log.info("PUT");
        BeanUtils.copyProperties(resume,resumeFromDB,"id","author","createdAt");
        return new ResponseEntity<>(resumeRepo.save(resumeFromDB), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_USER'))")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable("id") Resume resume)
    {   log.info("DELETE" + resume.toString());
            resumeRepo.delete(resume);
      //  resumeService.deleteResumeById(resume.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' || hasRole('ROLE_USER'))")
    @GetMapping("/author/{id}")
    public ResponseEntity<Long> getAuthorId(@PathVariable("id") Resume resume){
        log.info("author GET");
        return new ResponseEntity<>(resume.getAuthor().getId(),HttpStatus.OK);
    }

}
