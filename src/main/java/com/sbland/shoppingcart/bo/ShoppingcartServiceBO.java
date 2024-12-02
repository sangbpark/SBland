package com.sbland.shoppingcart.bo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.product.bo.ProductStockBO;
import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;
import com.sbland.shoppingcart.dto.ShoppingcartCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShoppingcartServiceBO {
	private final ProductStockBO productStockBO;
	private final ShoppingcartBO shoppingcartBO;
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;
	private final ObjectMapper objectMapper;
	
	public Response<Integer> shoppingcartEdit(Long userId, Long productId, int count) {
		Integer quantity = productStockBO.getProductStockByProductId(productId).getQuantity();
		
		if (quantity == 0 ) {
			return shoppingcartBO.deleteShoppingcartByUserIdAndProductId(userId, productId)
					.<Integer>toBuilder()
					.data(quantity)
					.build();
		} else if (quantity >= count) {
			return shoppingcartBO.updateShoppingcartByUserIdAndProductId(userId, productId, count)
					.<Integer>toBuilder()
					.data(quantity)
					.build();
		} else {
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("재고보다 구매 수량이 많습니다.")
					.data(quantity)
					.build();
		}			
	}
	
	public List<ShoppingcartCardDTO> getShoppingcartByUserId(Long id) {
		List<ShoppingcartCardDTO> shoppingcartCardDTOList= shoppingcartBO.getShoppingcartByUserId(id)
				.stream()
				.map(shoppingcart -> {
					ShoppingcartCardDTO shoppingcartCardDTO = objectMapper.convertValue(shoppingcart, ShoppingcartCardDTO.class);
					Long productId = shoppingcart.getProductId();
					ProductThumbnailCardDTO productThumbnailCardDTO = productThumbnailCardDTOBO.getProductThumbnailCardDTOByProductId(productId);
					return shoppingcartCardDTO
							.toBuilder()
							.name(productThumbnailCardDTO.getName())
							.ThumbnailImage(productThumbnailCardDTO.getThumbnailImage())
							.price(productThumbnailCardDTO.getPrice())
							.build();
				})
				.collect(Collectors.toList());
		
		return shoppingcartCardDTOList;
	}
}
