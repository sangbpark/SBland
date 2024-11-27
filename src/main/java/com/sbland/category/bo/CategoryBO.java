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
	public void addCategory(int depth, String name, List<Integer> parentIds) {
		if (depth == 0) {
			CategoryEntity category = categoryRepository.findFirstByOrderByIdDesc().orElse(null);
			if (category == null) {
				saveCategory(name, depth, 1, 2);
				return;
			}
			if (category.getDepth() == 0) {
				saveCategory(name, depth, category.getRight_value() + 1, category.getRight_value() + 2);
				return;
			} else {
				saveCategory(name, depth, category.getRight_value() + 2, category.getRight_value() + 3);
				return;
			}
		}
		CategoryEntity category = categoryRepository.findById(parentIds.get(parentIds.size() - 1)).orElse(null);
		int id = category.getRight_value();
		List<CategoryEntity> categorys = categoryRepository.findByIdGreaterThanOrderByIdDesc(id);
		if (categorys.isEmpty()) {
			saveCategory(name, depth, id, id + 1);
			for (int categoryId : parentIds) {
				CategoryEntity pCategory = categoryRepository.findById(categoryId).orElse(null);
				saveCategory(pCategory.toBuilder().right_value(pCategory.getRight_value() + 2).build());
			}				
		}
	};
	
	private void saveCategory(String name, int depth, int id, int rightValue) {
		categoryRepository.save(CategoryEntity
				.builder()
				.id(id)
				.name(name)
				.depth(depth)
				.right_value(rightValue)
				.build());
	}
	private void saveCategory(CategoryEntity category) {
		categoryRepository.save(category);
	}
}
