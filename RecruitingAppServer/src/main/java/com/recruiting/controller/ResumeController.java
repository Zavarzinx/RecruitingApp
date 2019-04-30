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
    public ResumeController(ResumeRepo resumeRepo, UserRepo userRepo, UserService userService) {
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

    @GetMapping("/all/user")
    public ResponseEntity<List<Resume>> getAllResumeByUser(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findByUsername(userDetails.getUsername());
        log.info(resumeRepo.findAllByAuthor(user) + " successfully get from DB");

        if (resumeRepo.findAllByAuthor(user) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resumeRepo.findAllByAuthor(user), HttpStatus.OK);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Resume> createResume(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Resume resume){
        log.info(userDetails + "current user while creating author");
        log.info("resume without author" + resume);
        log.info("username " + userRepo.findByUsername(userDetails.getUsername()));
        log.info("username " + userDetails.getUsername());
        resume.setAuthor(userRepo.findByUsername(userDetails.getUsername()));
        log.info("POST");
        log.info(resume.toString() + " successfully saved into DB");
        return new ResponseEntity<>(resumeRepo.save(resume), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable ("id") Resume resumeFromDB, @RequestBody Resume resume){
        log.info("PUT");
        BeanUtils.copyProperties(resume,resumeFromDB,"id","author");
        return new ResponseEntity<>(resumeRepo.save(resumeFromDB), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteResume(@PathVariable("id") Resume resume)
    {   log.info("DELETE");
        resumeRepo.delete(resume);
    }


}
