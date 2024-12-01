package com.sbland.shoppingcart.bo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;
import com.sbland.shoppingcart.domain.Shoppingcart;
import com.sbland.shoppingcart.dto.ShoppingcartCardDTO;
import com.sbland.shoppingcart.mapper.ShoppingcartMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShoppingcartBO {
	private final ShoppingcartMapper shoppingcartMapper;
	private final ObjectMapper objectMapper;
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;
	
	public int addShoppingcart(Long userId, Long productId, int productCount) {
		return shoppingcartMapper.insertShoppingcart(userId, productId, productCount);
	}
	
	public List<ShoppingcartCardDTO> getShoppingcartByUserId(Long id) {
		List<ShoppingcartCardDTO> shoppingcartCardDTOList= shoppingcartMapper.selectShoppingcartByUserId(id)
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
	
	public Response updateShoppingcartByUserIdAndProductId(Long userId, Long productId, int count) {
		Shoppingcart shoppingcart = shoppingcartMapper.selectShoppingcartByUserIdAndProductId(userId, productId);
		if (shoppingcart == null) {
			return Response
					.builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니에 아이템이 없습니다.")
					.build();
					
		}
		if (shoppingcartMapper.updateShoppingcart(userId, productId, count) > 0) {
			return Response
					.builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("장바구니 수정에 성공했습니다.")
					.build();
		} else {
			return Response
					.builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니 수정에 실패했습니다.")
					.build();
		}
		
	}
	
	public Response deleteShoppingcartByUserIdAndProductId(Long userId, Long productId) {
		Shoppingcart shoppingcart = shoppingcartMapper.selectShoppingcartByUserIdAndProductId(userId, productId);
		if (shoppingcart == null) {
			return Response
					.builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니에 아이템이 없습니다.")
					.build();					
		}
		if(shoppingcartMapper.deleteShoppingcartByUserIdAndProductId(userId, productId) > 0) {
			return Response
					.builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("장바구니 삭제에 성공했습니다.")
					.build();
		} else {
			return Response
					.builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니 삭제를 실패했습니다.")
					.build();
		}
	};
}
