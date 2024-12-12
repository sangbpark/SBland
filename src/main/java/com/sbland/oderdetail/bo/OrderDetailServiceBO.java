package com.sbland.oderdetail.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.objectmapper.ObjectMapperFactory;
import com.sbland.oderdetail.domain.OrderDetail;
import com.sbland.oderdetail.dto.OrderDetailDTO;
import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderDetailServiceBO {
	private final OrderDetailBO orderDetailBO;
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;
	
	public List<OrderDetailDTO> getOrderDetailDTO(Long orderId) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		List<OrderDetail> orderDetailList = orderDetailBO.getOrderDetailByOrderId(orderId)
				.stream()
				.sorted((orderDetail1, orderDetail2) -> Long.compare(orderDetail1.getId(), orderDetail2.getId()))
                .collect(Collectors.toList());
		List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
		if (orderDetailList.isEmpty()) {
			return orderDetailDTOList;
		} else {
			orderDetailDTOList = orderDetailList
					.stream()
					.map(orderDetail -> camelObjectMapper.convertValue(orderDetail, OrderDetailDTO.class))
					.collect(Collectors.toList());
			
			orderDetailDTOList.replaceAll(orderDetailDTO -> {
				Long productId = orderDetailDTO.getProductId();
				ProductThumbnailCardDTO productThumbnailCard = productThumbnailCardDTOBO.getProductThumbnailCardDTOByProductId(productId);
				return orderDetailDTO.toBuilder()
								.name(productThumbnailCard.getName())
								.url(productThumbnailCard.getThumbnailImage())
								.build();
			});
			
			return orderDetailDTOList;
		}
	}
}
