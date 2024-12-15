package com.sbland.order.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.order.domain.Order;
import com.sbland.order.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderBO {
	private final OrderMapper orderMapper;
	
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
					.data(orderList)
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
	
	public Response<List<Order>> getOrderByStatus(String status, int count, int offset) {
		List<Order> orderList = orderMapper.selectOrderByStatus(status, count, offset);
		if (orderList.isEmpty()) {
			return Response
					.<List<Order>>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("주문번호나 유저를 확인 해주세요.")
					.data(orderList)
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
	
	public int getOrderListSizeByStatus(String status) {
		return orderMapper.selectOrderListSizeByStatus(status);
	}

	public Response<Long> addOrder(Order order) {	
		int result = orderMapper.insertOrder(order);
		
		if (result == 1) {
			return  Response
					.<Long>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("주문 성공")
					.data(order.getId())
					.build();
		} else {
			log.info("[주문] db 인설트 실패 userId:{}", order.getUserId());
			return  Response
					.<Long>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("주문 실패")
					.data(0L)
					.build();
		}
	};
	
	public Response<Boolean> updateOrderStatus(Long orderId, Long userId, String status) {
		Order order = orderMapper.selectOrderByIdAndUserId(orderId, userId);
		if (order == null) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("주문이 없습니다.")
					.build();
		} else {
			int result = orderMapper.updateOrderStatus(orderId, userId, status);
			if (result > 0) {
				return Response
						.<Boolean>builder()
						.code(HttpStatusCode.OK.getCodeValue())
						.message("주문취소 신청 완료.")
						.build();
			} else {
				return Response
						.<Boolean>builder()
						.code(HttpStatusCode.FAIL.getCodeValue())
						.message("주문취소 신청 실패.")
						.build();
			}
		}
	}
}
