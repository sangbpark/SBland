package com.sbland.product.bo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ProductThumbnailCardDTOBOTest {
	@Autowired
	ProductThumbnailCardDTOBO productThumbnailCardDTOBO;

	@Test
	void 카테고리상품리스트테스트() {
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOByCateogry(3, 4);
		log.info("[테스트] productThumbnailCardDTOList:{}",productThumbnailCardDTOList.size());
	}

}
