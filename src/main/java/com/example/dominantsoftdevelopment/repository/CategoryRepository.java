package com.example.dominantsoftdevelopment.repository;

import com.example.dominantsoftdevelopment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findByParentCategoryIsNullAndDeletedFalse();

    List<Category> findByParentCategoryIdAndDeletedFalse(Long parentCategory_id);

    Optional<Category> findByIdAndDeletedFalse(Long id);
}
