package com.recruiting.repo;

import com.recruiting.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepo extends JpaRepository<Resume,Long>{
   List<Resume> findAllByAuthor(String author);
}
