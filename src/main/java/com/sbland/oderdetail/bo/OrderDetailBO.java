package com.sbland.oderdetail.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.oderdetail.domain.OrderDetail;
import com.sbland.oderdetail.mapper.OrderDetailMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderDetailBO {
	private final OrderDetailMapper orderDetailMapper;
	
	public List<OrderDetail> getOrderDetailByOrderId(Long orderId) {
		List<OrderDetail> orderDetailList = orderDetailMapper.selectOrderDetailByOrderId(orderId);
		if (orderDetailList.isEmpty()) {
			log.info("[주문상세] 주문명세서는 있는데 주문상세가 없음 orderId:{}", orderId);
		}
		return orderDetailList;
	}
	
	public Response<Boolean> addOrderDetail(List<OrderDetail> orderDetailList) {
		int result = orderDetailMapper.insertOrderDetail(orderDetailList);
		if (result == 1) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("성공")
					.data(true)
					.build();
		} else {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("성공")
					.data(true)
					.build();
		}
	}
}
