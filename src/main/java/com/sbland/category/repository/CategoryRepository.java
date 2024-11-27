package com.sbland.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbland.category.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
	public Optional<CategoryEntity> findFirstByOrderByCodeDesc();
	
	public Optional<CategoryEntity> findByCode(int code);
	
	public List<CategoryEntity> findByCodeGreaterThanOrderByCodeDesc(int code);
}
