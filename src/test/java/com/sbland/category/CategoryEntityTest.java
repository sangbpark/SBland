package com.sbland.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.category.entity.CategoryEntity;
import com.sbland.category.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class CategoryEntityTest {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Test
	void test() {
		CategoryEntity category = CategoryEntity.builder()
				.id(1)
				.left_value(1)
				.right_value(1)
				.name("ㅎㅇ")
				.build();
		CategoryEntity result = categoryRepository.save(category);
		result.getCreated_at();
		log.info(" " + result + result.getCreated_at());
	}

}
