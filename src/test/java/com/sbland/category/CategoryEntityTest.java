package com.sbland.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbland.category.repository.CategoryRepository;


@SpringBootTest
class CategoryEntityTest {
	@Autowired
	CategoryRepository categoryRepository;

	
	@Test
	void test() {
	}

}
