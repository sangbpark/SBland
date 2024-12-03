package com.sbland.category.bo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.category.dto.CategoryDTO;
import com.sbland.category.entity.CategoryEntity;
import com.sbland.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryBO {
	private final CategoryRepository categoryRepository;
	private final ObjectMapper objectMapper;
	
	@Transactional
	public void addCategory(int depth, String name, List<Integer> parentCode) {
		if (depth == 0) {
			CategoryEntity category = categoryRepository.findFirstByOrderByCodeDesc().orElse(null);
			if (category == null) {
				saveCategory(name, depth, 1, 2);
				return;
			}
			if (category.getDepth() == 0) {
				saveCategory(name, depth, category.getRightValue() + 1, category.getRightValue() + 2);
				return;
			} else {
				saveCategory(name, depth, category.getRightValue() + 1 + category.getDepth(), category.getRightValue() + 2 + category.getDepth());
				return;
			}
		}
		CategoryEntity category = categoryRepository.findByCode(parentCode.get(parentCode.size() - 1)).orElse(null);
		int code = category.getRightValue();
		List<CategoryEntity> categorys = categoryRepository.findByCodeGreaterThanOrderByCodeDesc(code);
		if (categorys.isEmpty()) {
			saveCategory(name, depth, code, code + 1);
			for (int categoryCode : parentCode) {
				CategoryEntity pCategory = categoryRepository.findByCode(categoryCode).orElse(null);
				saveCategory(pCategory.toBuilder().rightValue(pCategory.getRightValue() + 2).build());
			}
			return;
		}
		
		for (CategoryEntity temp : categorys) {
			saveCategory(temp.toBuilder().code(temp.getCode() + 2).rightValue(temp.getRightValue() + 2).build());
		}
		saveCategory(name, depth, code, code + 1);
		for (int pCode : parentCode) {
			category = categoryRepository.findByCode(pCode).orElse(null);
			saveCategory(category.toBuilder().rightValue(category.getRightValue() + 2).build());
		}
	};
	
	public List<CategoryEntity> getCategoryAll() {
		return categoryRepository.findAllByOrderByCode();
	}
	
	@Transactional
	public void deleteCategory(int code, List<Integer> parentCode) {
		
		CategoryEntity category = categoryRepository.findByCode(code).orElse(null);
		int deleteScope = category.getRightValue();
		int changeCode = category.getRightValue() - code + 1;		
		List<CategoryEntity> categorys = categoryRepository.findByCodeGreaterThanOrderByCodeAsc(deleteScope);
		categoryRepository.deleteByColumnValueBetween(code, deleteScope);
		if (!categorys.isEmpty()) {
			for (CategoryEntity temp : categorys) {
				saveCategory(temp.toBuilder().code(temp.getCode() - changeCode).rightValue(temp.getRightValue() - changeCode).build());
			}
		}
		if (!parentCode.isEmpty()) {
			for (int pCode : parentCode) {
				category = categoryRepository.findByCode(pCode).orElse(null);
				saveCategory(category.toBuilder().rightValue(category.getRightValue() - changeCode).build());
			}
		}	
	}
	
	public CategoryEntity getCategoryByCode(int code) {
		return categoryRepository.findByCode(code).orElse(null);
	}
	
	
	public List<CategoryDTO> getCategoryMenu() { 
		List<CategoryDTO> categoryDTOList = categoryRepository.findAllByOrderByCode()
				.stream()
				.map(categoryEntity -> objectMapper.convertValue(categoryEntity, CategoryDTO.class))
				.collect(Collectors.toList());
		
		List<CategoryDTO> categoryDTOListTree = new ArrayList<>();
		Deque<CategoryDTO> stack = new ArrayDeque<>();
	
		for (CategoryDTO category : categoryDTOList) {
		        while (!stack.isEmpty() && stack.peek().getRightValue() < category.getCode()) {
		            stack.pop();
		        }	
		        if (stack.isEmpty()) {
		        	categoryDTOListTree.add(category);
		        } else {
		            CategoryDTO parent = stack.peek();
		            parent.getChildCategory().add(category);
		        }
		        stack.push(category);
		}
		return categoryDTOListTree;
	}
	
	private void saveCategory(String name, int depth, int code, int rightValue) {
		categoryRepository.save(CategoryEntity
				.builder()
				.name(name)
				.code(code)
				.depth(depth)
				.rightValue(rightValue)
				.build());
	}
	private void saveCategory(CategoryEntity category) {
		categoryRepository.save(category);
	}
}
