package com.sbland.product.bo;

import java.util.List;
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
}
