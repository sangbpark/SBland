package com.sbland.shoppingcart.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.shoppingcart.domain.Shoppingcart;
import com.sbland.shoppingcart.mapper.ShoppingcartMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingcartBO {
	private final ShoppingcartMapper shoppingcartMapper;

	
	public Response<Integer> addShoppingcart(Long userId, Long productId, int productCount) {
		int result = shoppingcartMapper.insertShoppingcart(userId, productId, productCount);
		if (result == 1) {
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("장바구니 저장 성공")
					.build();
		} else {
			log.info("[장바구니] 인설트 실패 userId:{} productId:{}",userId,productId);
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니에 상품을 담지 못했습니다.")
					.build();
		}
	}
	
	public List<Shoppingcart> getShoppingcartByUserId(Long id) {
		List<Shoppingcart> shoppingcartList= shoppingcartMapper.selectShoppingcartByUserId(id);		
		return shoppingcartList;
	}
	
	public Shoppingcart getShoppingcartByUserIdAndProductId(Long userId, Long productId) {
		return shoppingcartMapper.selectShoppingcartByUserIdAndProductId(userId, productId);
	};
	public Response<Integer> updateShoppingcartByUserIdAndProductId(Long userId, Long productId, int count) {
		Shoppingcart shoppingcart = shoppingcartMapper.selectShoppingcartByUserIdAndProductId(userId, productId);
		if (shoppingcart == null) {
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니에 아이템이 없습니다.")
					.build();
					
		}
		if (shoppingcartMapper.updateShoppingcart(userId, productId, count) > 0) {
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("장바구니 수정에 성공했습니다.")
					.build();
		} else {
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니 수정에 실패했습니다.")
					.build();
		}
		
	}
	
	public Response<Integer> deleteShoppingcartByUserIdAndProductId(Long userId, Long productId) {
		Shoppingcart shoppingcart = shoppingcartMapper.selectShoppingcartByUserIdAndProductId(userId, productId);
		if (shoppingcart == null) {
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니에 아이템이 없습니다.")
					.build();					
		}
		if(shoppingcartMapper.deleteShoppingcartByUserIdAndProductId(userId, productId) > 0) {
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("장바구니 삭제에 성공했습니다.")
					.build();
		} else {
			return Response
					.<Integer>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("장바구니 삭제를 실패했습니다.")
					.build();
		}
	};
	
	public int deleteShoppingcartByUserId(Long userId) {
		return shoppingcartMapper.deleteShoppingcartByUserId(userId);
	}
}
