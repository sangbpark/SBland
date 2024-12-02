package com.sbland.category.bo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.category.dto.CategoryRootDTO;
import com.sbland.category.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class CategoryBOTest {
	@Autowired
	CategoryBO cb;
	@Autowired
	CategoryRepository cr;
	
	@Transactional
	@Test
	void 카테고리인서트테스트() {
		List<Integer> result = new ArrayList<>();
		result.add(111);
		cb.addCategory(0, "etc", null);
		


	}
	
	@Transactional
	@Test
	void 카테고리딜리트테스트() {
//		List<Integer> result = new ArrayList<>();
//		cb.deleteCategory(13, result);

	}
	
	@Test
	void 카테고리메뉴테스트() {
		List<CategoryRootDTO> categoryRootDTOList = cb.getCategoryMenu();
		log.info("[테스트] categoryRootDTOList:{}", categoryRootDTOList);
	}
}
