package com.recruiting.controller;

import com.recruiting.domain.Job;
import com.recruiting.repo.JobRepo;
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

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/api/recruiter/")
public class RecruiterController {

    private final JobRepo jobRepo;

    private final UserRepo userRepo;

    @Autowired
    public RecruiterController(JobRepo jobRepo, UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Job> createJob(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Job job){
        log.info("Job creating" + job);
        job.setAuthor(userRepo.findByUsername(userDetails.getUsername()));
        return new ResponseEntity<>(jobRepo.save(job), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<Job>> jobList(){
        if (jobRepo.findAll() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobRepo.findAll(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Job> updateJob(@PathVariable ("id") Job jobFromDB, @RequestBody Job resume){
        log.info("PUT");
        BeanUtils.copyProperties(resume,jobFromDB,"id","author");
        return new ResponseEntity<>(jobRepo.save(jobFromDB), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteJob(@PathVariable("id") Job job)
    {   log.info("DELETE");
        jobRepo.delete(job);
    }

}
