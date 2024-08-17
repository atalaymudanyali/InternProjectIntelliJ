package com.internproject.quizApp.repo;

import com.internproject.quizApp.model.exam.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}