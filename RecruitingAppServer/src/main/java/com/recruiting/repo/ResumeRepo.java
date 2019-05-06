package com.recruiting.repo;

import com.recruiting.domain.Resume;
import com.recruiting.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepo extends JpaRepository<Resume,Long>{
   List<Resume> findAllByAuthor(User author);
}
