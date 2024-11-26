package com.sbland.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbland.category.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
