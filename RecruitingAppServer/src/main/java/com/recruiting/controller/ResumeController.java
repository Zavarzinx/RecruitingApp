package com.recruiting.controller;

import com.recruiting.domain.Resume;
import com.recruiting.domain.User;
import com.recruiting.dto.ResumeDto;
import com.recruiting.repo.ResumeRepo;
import com.recruiting.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@Slf4j
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeRepo resumeRepo;

    private final UserRepo userRepo;

    @Autowired
    public ResumeController(ResumeRepo resumeRepo,UserRepo userRepo) {
        this.resumeRepo = resumeRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resume>> resumesList(){
        return new ResponseEntity<>(resumeRepo.findAll(), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public  ResponseEntity<Resume> getResume(@PathVariable("id") Resume resume){
        log.info("GET");
        log.info(resume.toString() + " successfully get from DB");
        return new ResponseEntity<>(resume, HttpStatus.OK);
    }

    @GetMapping("/author/{name}")
    public ResponseEntity<List<Resume>> getAllResumeByAuthor(@PathVariable("name") String author){
        log.info(resumeRepo.findAllByAuthor(author) + " successfully get from DB");
        return new ResponseEntity<>(resumeRepo.findAllByAuthor(author), HttpStatus.OK);
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume){
        log.info("POST");
        log.info(resume.toString() + " successfully saved into DB");
        return new ResponseEntity<>(resumeRepo.save(resume), HttpStatus.OK);
    }

    @PutMapping
    public Resume updateResume(@PathVariable ("id") Resume resumeFromDB, @RequestBody Resume resume){
        log.info("PUT");
        BeanUtils.copyProperties(resume,resumeFromDB,"id");
        return resumeRepo.save(resumeFromDB);
    }

    @DeleteMapping("{id}")
    public void deleteResume(@PathVariable("id") Resume resume)
    {   log.info("DELETE");
        resumeRepo.delete(resume);
    }
    /*

    https://github.com/paweljw/bookstore-frontend/tree/master/src/components

    https://github.com/drucoder/sarafan/blob/EmbeddedId/src/main/java/letscode/sarafan/service/MessageService.java

    https://github.com/shimh-develop/blog-vue-springboot/blob/2a40db7a09175ddeab3f5c352e0268185387569b/blog-api/src/main/java/com/shimh/controller/ArticleController.java

        axios???
    }
     */


}
