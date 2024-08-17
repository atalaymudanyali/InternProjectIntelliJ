package com.internproject.quizApp.repo;

import com.internproject.quizApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<Role,Long> {

}