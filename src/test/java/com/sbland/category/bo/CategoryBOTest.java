package com.sbland.category.bo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbland.category.repository.CategoryRepository;


@SpringBootTest
class CategoryBOTest {
	@Autowired
	CategoryBO cb;
	@Autowired
	CategoryRepository cr;
	
	@Test
	void 카테고리인서트테스트() {
//		List<Integer> result = new ArrayList<>();
//		result.add(47);
//		cb.addCategory(1, "테스트25", result);

	}
	
	@Test
	void 카테고리딜리트테스트() {
		List<Integer> result = new ArrayList<>();
		cb.deleteCategory(13, result);

	}
}
