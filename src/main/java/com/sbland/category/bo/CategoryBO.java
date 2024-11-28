package com.sbland.category.bo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.category.entity.CategoryEntity;
import com.sbland.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryBO {
	private final CategoryRepository categoryRepository;
	
	@Transactional
	public void addCategory(int depth, String name, List<Integer> parentCode) {
		if (depth == 0) {
			CategoryEntity category = categoryRepository.findFirstByOrderByCodeDesc().orElse(null);
			if (category == null) {
				saveCategory(name, depth, 1, 2);
				return;
			}
			if (category.getDepth() == 0) {
				saveCategory(name, depth, category.getRight_value() + 1, category.getRight_value() + 2);
				return;
			} else {
				saveCategory(name, depth, category.getRight_value() + 1 + category.getDepth(), category.getRight_value() + 2 + category.getDepth());
				return;
			}
		}
		CategoryEntity category = categoryRepository.findByCode(parentCode.get(parentCode.size() - 1)).orElse(null);
		int code = category.getRight_value();
		List<CategoryEntity> categorys = categoryRepository.findByCodeGreaterThanOrderByCodeDesc(code);
		if (categorys.isEmpty()) {
			saveCategory(name, depth, code, code + 1);
			for (int categoryCode : parentCode) {
				CategoryEntity pCategory = categoryRepository.findByCode(categoryCode).orElse(null);
				saveCategory(pCategory.toBuilder().right_value(pCategory.getRight_value() + 2).build());
			}
			return;
		}
		
		for (CategoryEntity temp : categorys) {
			saveCategory(temp.toBuilder().code(temp.getCode() + 2).right_value(temp.getRight_value() + 2).build());
		}
		saveCategory(name, depth, code, code + 1);
		for (int pCode : parentCode) {
			category = categoryRepository.findByCode(pCode).orElse(null);
			saveCategory(category.toBuilder().right_value(category.getRight_value() + 2).build());
		}
	};
	
	@Transactional
	public void deleteCategory(int code, List<Integer> parentCode) {
		
		CategoryEntity category = categoryRepository.findByCode(code).orElse(null);
		int deleteScope = category.getRight_value();
		int changeCode = category.getRight_value() - code + 1;		
		List<CategoryEntity> categorys = categoryRepository.findByCodeGreaterThanOrderByCodeAsc(deleteScope);
		categoryRepository.deleteByColumnValueBetween(code, deleteScope);
		if (!categorys.isEmpty()) {
			for (CategoryEntity temp : categorys) {
				saveCategory(temp.toBuilder().code(temp.getCode() - changeCode).right_value(temp.getRight_value() - changeCode).build());
			}
		}
		if (!parentCode.isEmpty()) {
			for (int pCode : parentCode) {
				category = categoryRepository.findByCode(pCode).orElse(null);
				saveCategory(category.toBuilder().right_value(category.getRight_value() - changeCode).build());
			}
		}	
	}
	
	private void saveCategory(String name, int depth, int code, int rightValue) {
		categoryRepository.save(CategoryEntity
				.builder()
				.name(name)
				.code(code)
				.depth(depth)
				.right_value(rightValue)
				.build());
	}
	private void saveCategory(CategoryEntity category) {
		categoryRepository.save(category);
	}
}
