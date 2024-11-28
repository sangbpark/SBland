package com.sbland.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbland.category.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
	public Optional<CategoryEntity> findFirstByOrderByCodeDesc();
	
	public Optional<CategoryEntity> findByCode(int code);
	
	public List<CategoryEntity> findByCodeGreaterThanOrderByCodeAsc(int code);
	
	public int deleteByCode(int code);
	
	public List<CategoryEntity> findByCodeGreaterThanOrderByCodeDesc(int code);
	
	@Modifying
    @Query("DELETE FROM CategoryEntity e WHERE e.code BETWEEN :startValue AND :endValue")
    public void deleteByColumnValueBetween(@Param("startValue") int startValue, @Param("endValue") int endValue);
}
