package com.sbland.homepage.bo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ProductThumbnailCardDTOBOTest {
	@Autowired
	ProductThumbnailCardDTOBO homepageBO;

	@Test
	void 홈페이지인기상품테스트() {
		List<ProductThumbnailCardDTO> list = homepageBO.getBestProductTop3();
		log.info("[테스트] list:{}", list);
	}

}
