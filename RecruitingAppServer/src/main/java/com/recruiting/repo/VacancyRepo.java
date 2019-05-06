package com.recruiting.repo;

import com.recruiting.domain.User;
import com.recruiting.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy,Long> {
    List<Vacancy> findAllByAuthor(User author);
}
