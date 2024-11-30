package com.sbland.product.bo;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sbland.product.domain.ProductRank;
import com.sbland.product.mapper.ProductRankMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductRankBO {
	private final ProductRankMapper productRankMapper;
	
	public int addProductRankList(List<Long> id) {
		List<ProductRank> productRankList = id.stream()
				.map(idValue -> ProductRank.builder()
						.productId(idValue)
						.totalCount(0)
						.build())
				.collect(Collectors.toList());
		return productRankMapper.InsertProductRankList(productRankList);
	};
	
	public int updateProductRankByProductId(Long id, int salesVolume) {
		return productRankMapper.updateProductRankByProductId(id, salesVolume);
	}
	
	public List<ProductRank> getProductRankListTop(int count) {
		return productRankMapper.selectProductRanklistTop(count);
	}
	
	public void initialUpdateProductRankALL() {
		Random random = new Random();
		productRankMapper.selectProductRankAll()
				.forEach(ProductRank -> {
							int randomSales = random.nextInt(100);
							productRankMapper.updateProductRankByProductId(
									ProductRank.getProductId(), randomSales);							
						});
	
	}
}
