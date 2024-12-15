package com.sbland.order.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.objectmapper.ObjectMapperFactory;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.oderdetail.bo.OrderDetailBO;
import com.sbland.oderdetail.bo.OrderDetailServiceBO;
import com.sbland.oderdetail.domain.OrderDetail;
import com.sbland.oderdetail.dto.OrderDetailPaymentDTO;
import com.sbland.order.domain.Order;
import com.sbland.order.dto.OrderDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceBO {
	private final OrderBO orderBO;
	private final OrderDetailBO orderDetailBO;
	private final OrderDetailServiceBO orderDetailServiceBO;
	
	public Response<OrderDTO> getOrderDTOByOrderId(Long id) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		Order order = orderBO.getOrderById(id).getData();
		OrderDTO orderDTO = camelObjectMapper.convertValue(order, OrderDTO.class);
		return Response
				.<OrderDTO>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("주문명세서를 가져오는데 성공했습니다.")
				.data(orderDTO
						.toBuilder()
						.orderDetailDTOList(orderDetailServiceBO.getOrderDetailDTO(id))
						.build())
				.build();
	}

	private Response<List<OrderDTO>> getOrderDTOByOrderIdList(Long userId, int count, int offset) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		List<Order> orderList = orderBO.getOrderByUserId(userId, count, offset).getData();
		List<OrderDTO> orderDTOList = new ArrayList<>();
		if (orderList.isEmpty()) {
			return Response.<List<OrderDTO>>builder()
							.code(HttpStatusCode.FAIL.getCodeValue())
							.message("주문명세서를 가져오는데 실패했습니다.")
							.data(orderDTOList)
							.build();
		}
								
		orderDTOList = orderList
				.stream()
				.map(order -> camelObjectMapper.convertValue(order, OrderDTO.class))
				.collect(Collectors.toList());
		
		orderDTOList.replaceAll(orderDTO -> {
								return orderDTO
										.toBuilder()
										.orderDetailDTOList(orderDetailServiceBO.getOrderDetailDTO(orderDTO.getId()))
										.build();
								});
		
		orderDTOList.stream()
					.sorted((orderDTO1, orderDTO2) 
							-> orderDTO2.getCreatedAt().compareTo(orderDTO1.getCreatedAt()))
					.collect(Collectors.toList());
		
		return Response
				.<List<OrderDTO>>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("주문명세서를 가져오는데 성공했습니다.")
				.data(orderDTOList)
				.build();
	}
	
	private Response<List<OrderDTO>> getOrderDTOByStatusList(String status, int count, int offset) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		List<Order> orderList = orderBO.getOrderByStatus(status, count, offset).getData();
		List<OrderDTO> orderDTOList = new ArrayList<>();
		if (orderList.isEmpty()) {
			return Response.<List<OrderDTO>>builder()
							.code(HttpStatusCode.FAIL.getCodeValue())
							.message("주문명세서를 가져오는데 실패했습니다.")
							.data(orderDTOList)
							.build();
		}
								
		orderDTOList = orderList
				.stream()
				.map(order -> camelObjectMapper.convertValue(order, OrderDTO.class))
				.collect(Collectors.toList());
		
		orderDTOList.replaceAll(orderDTO -> {
								return orderDTO
										.toBuilder()
										.orderDetailDTOList(orderDetailServiceBO.getOrderDetailDTO(orderDTO.getId()))
										.build();
								});
		
		orderDTOList.stream()
					.sorted((orderDTO1, orderDTO2) 
							-> orderDTO2.getCreatedAt().compareTo(orderDTO1.getCreatedAt()))
					.collect(Collectors.toList());
		
		return Response
				.<List<OrderDTO>>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("주문명세서를 가져오는데 성공했습니다.")
				.data(orderDTOList)
				.build();
	}
		
	public Response<List<OrderDTO>> getResponseOrderDTOListByUserId(Long userId, int count, int offset) {
		return getOrderDTOByOrderIdList(userId, count, offset);
	}
	
	public Response<List<OrderDTO>> getResponseOrderDTOListByStatus(String status, int count, int offset) {
		return getOrderDTOByStatusList(status, count, offset);
	}
	
	public Response<Long> addOrderAndOrderDetail(Long userId, String merchantUid, int amount, int deliveryfee
			, String status, String shippingAddress, List<OrderDetailPaymentDTO> orderDetailMapList) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		Order order = Order
				.builder()
				.userId(userId)
				.merchantUid(merchantUid)
				.amount(amount)
				.deliveryfee(deliveryfee)
				.status(status)
				.shippingAddress(shippingAddress)
				.build();
		Response<Long> response = orderBO.addOrder(order);
		if (response.getData() == 0) {
			return Response
					.<Long>builder()
					.code(response.getCode())
					.message(response.getMessage())
					.data(order.getId())
					.build();
		}
		
		List<OrderDetail> orderDetailList = orderDetailMapList
				.stream()
				.map(orderDetailMap -> camelObjectMapper
						.convertValue(orderDetailMap, OrderDetail.class)
						.toBuilder()
						.orderId(response.getData())
						.build()
				)
				.collect(Collectors.toList());
		
		Response<Boolean> detailResonse = orderDetailBO.addOrderDetail(orderDetailList);
		if (detailResonse.getData()) {
			return Response
					.<Long>builder()
					.code(detailResonse.getCode())
					.message(detailResonse.getMessage())
					.data(order.getId())
					.build();
		} else {
			return Response
					.<Long>builder()
					.code(detailResonse.getCode())
					.message(detailResonse.getMessage())
					.data(order.getId())
					.build();
		}
	}

}
