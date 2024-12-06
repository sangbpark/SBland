package com.sbland.order.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.common.uid.UidGenerator;
import com.sbland.order.domain.Order;
import com.sbland.order.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderBO {
	private final OrderMapper orderMapper;
	private final UidGenerator uidGenerator;
	
	public Response<Order> getOrderById(Long id) {
		Order order = orderMapper.selectOrderById(id);
		if (order == null) {
			return Response
					.<Order>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("주문번호나 유저를 확인 해주세요.")
					.build();
		} else {
			return Response
			.<Order>builder()
			.code(HttpStatusCode.OK.getCodeValue())
			.message("주문 조회 성공.")
			.data(order)
			.build();
		}
	};
		
	public Response<List<Order>> getOrderByUserId(Long userId, int count, int offset) {
		List<Order> orderList = orderMapper.selectOrderByUserId(userId, count, offset);
		if (orderList.isEmpty()) {
			return Response
					.<List<Order>>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("주문번호나 유저를 확인 해주세요.")
					.build();
		} else {
			return Response
			.<List<Order>>builder()
			.code(HttpStatusCode.OK.getCodeValue())
			.message("주문 조회 성공.")
			.data(orderList)
			.build();
		}
	};
	
	public int getOrderListSizeByUserId(Long userId) {
		return orderMapper.selectOrderListSizeByUserId(userId);
	}

	public Response<Long> addOrder(Long userId, int totalPrice, int deliveryfee
			, String status, String shippingAddress) {
		
		Order order = Order
				.builder()
				.merchantUid(uidGenerator.getMerchantUid())
				.userId(userId)
				.amount(totalPrice)
				.deliveryfee(deliveryfee)
				.status(status)
				.shippingAddress(shippingAddress)
				.build();
		int result = orderMapper.insertOrder(order);
		
		if (result == 1) {
			return  Response
					.<Long>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("주문 성공")
					.data(order.getId())
					.build();
		} else {
			log.info("[주문] db 인설트 실패 userId:{}", userId);
			return  Response
					.<Long>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("주문 실패")
					.data(order.getId())
					.build();
		}
	};
}