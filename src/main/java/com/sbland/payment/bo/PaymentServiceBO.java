package com.sbland.payment.bo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.aop.payment.PaymentTransactional;
import com.sbland.common.objectmapper.ObjectMapperFactory;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.exception.PaymentException;
import com.sbland.oderdetail.dto.OrderDetailPaymentDTO;
import com.sbland.order.bo.OrderBO;
import com.sbland.order.bo.OrderServiceBO;
import com.sbland.order.domain.Order;
import com.sbland.payment.domain.Payment;
import com.sbland.payment.dto.PortoneToken;
import com.sbland.product.bo.ProductStockServiceBO;
import com.sbland.product.dto.ProductStockDTO;
import com.sbland.shoppingcart.bo.ShoppingcartBO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceBO {
	private final PaymentBO paymentBO;
	private final OrderBO orderBO;
	private final OrderServiceBO orderServiceBO;
	private final PaymentAutoBO paymentAutoBO;
	private final ProductStockServiceBO productStockServiceBO;
	private final ShoppingcartBO shoppingcartBO;
	
	@PaymentTransactional
    public Response<Boolean> addPaymentflow (String impUid, Long userId, int deliveryfee, String shippinAddress, List<OrderDetailPaymentDTO> orderDetailPaymentDTOList) {   	
		ObjectMapper snakeObjectMapper = new ObjectMapperFactory().getSnakeObjectMapper();
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		try {
			PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
	    	portoneToken = validateAndGetPortoneToken(portoneToken);
	    	Map<String, Object> response = paymentAutoBO.getVerify(impUid, portoneToken.getAccessToken()).block();
	    	if (response != null && (int) response.get("code") == 0) {
	    		boolean productStockResult = productStockServiceBO.updateProductStock(orderDetailPaymentDTOList
	    				.stream()
	    				.map(orderDetailPaymentDTO -> camelObjectMapper.convertValue(orderDetailPaymentDTO, ProductStockDTO.class))
	    				.collect(Collectors.toList())
	    				);
	    		if (!productStockResult) {
	    			throw new RuntimeException("상품 업데이트 실패 impUid: " + impUid + ", orderDetailPaymentDTOList: " + orderDetailPaymentDTOList );
	    		}
	    		
	    	    Map<String, Object> paymentData = (Map<String, Object>) response.get("response");
	    	    Payment payment = snakeObjectMapper.convertValue(paymentData, Payment.class);
	    	    Response<Long> orderResponse = orderServiceBO.addOrderAndOrderDetail(userId, payment.getMerchantUid(), payment.getAmount(), deliveryfee, "결제완료", shippinAddress, orderDetailPaymentDTOList);
	    	    int result = paymentBO.addPayment(payment
	    	    		.toBuilder()
	    	    		.orderId(orderResponse.getData())
	    	    		.userId(userId)
	    	    		.build());
	    	    if (result != 1) {
	    	    	throw new RuntimeException("페이먼트 결제정보 저장 실패 impUid: " + impUid );
	    	    } else {
	    	    	List<Long> productIdList = orderDetailPaymentDTOList
	    	    			.stream()
	    	    			.map(orderDetailPaymentDTO -> orderDetailPaymentDTO.getProductId())
	    	    			.collect(Collectors.toList());
	    	    	int shoppingcartResult = shoppingcartBO.deleteShoppingcartByUserIdAndProductIdList(userId ,productIdList);
	    	    	if (shoppingcartResult > 0) {
		    	    	return Response
		    					.<Boolean>builder()
		    					.code(HttpStatusCode.OK.getCodeValue())
		    					.message("성공")
		    					.data(true)
		    					.build();
	    	    	} else {
	    	    		throw new RuntimeException("쇼핑카트 제거 실패 prodcutIdList: " + productIdList );
	    	    	}
	    	    }
	    	} else {
	    		throw new RuntimeException("결제정보 인증실패 message: " + response.get("message"));
	    	}
    	} catch (RuntimeException  e) {
    		log.info("[결제] " + e.getMessage());
    		throw new PaymentException(impUid, e.getMessage());
    	}
    }
	
	public Response<Boolean> workPaymentCancel(String impUid, String reason, int amount) {
		ObjectMapper snakeObjectMapper = new ObjectMapperFactory().getSnakeObjectMapper();
		Payment payment = paymentBO.getPaymentByImpUid(impUid);		
		if (payment == null) {
			log.info("[결제] 결제취소 실패 결제 내역이 없음 impUid:{}",impUid);
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("결제확인 실패")
					.data(false)
					.build();
		}
		PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
		portoneToken = validateAndGetPortoneToken(portoneToken);
		Map<String, Object> response = paymentAutoBO.getPaymentCancel(impUid, reason, amount, portoneToken.getAccessToken()).block();
		if (response != null && (int)response.get("code") == 0) {
			Map<String, Object> responseData = (Map<String, Object>) response.get("response");
			Payment newPayment = snakeObjectMapper.convertValue(responseData, Payment.class);
			int result = paymentBO.updatePayment(newPayment
	    	    		.toBuilder()
	    	    		.orderId(payment.getOrderId())
	    	    		.userId(payment.getUserId())
	    	    		.build());
		    if (result != 1) {
		    	log.info("[결제] 결제취소 결제 업데이트 실패 impUid:{}",payment.getImpUid());
		    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.FAIL.getCodeValue())
    					.message("payment 결제취소 실패")
    					.build();
		    } else {
		    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.OK.getCodeValue())
    					.message("payment 결제취소 성공")
    					.build();
		    }
		} else if ( response != null && response.get("message").equals("결제취소에 실패하였습니다. [500626|기 취소 거래]")) {
			int result = paymentBO.updatePayment(payment
	    	    		.toBuilder()
	    	    		.orderId(payment.getOrderId())
	    	    		.userId(payment.getUserId())
	    	    		.status("cancel")
	    	    		.cancelAmount(amount)
	    	    		.cancelReason(reason)
	    	    		.cancelledAt(LocalDateTime.now())
	    	    		.build());
		    if (result != 1) {
		    	log.info("[결제] 결제취소 결제 업데이트 실패 impUid:{}",payment.getImpUid());
		    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.FAIL.getCodeValue())
    					.message("payment 결제취소 실패")
    					.build();
		    } else {
		    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.OK.getCodeValue())
    					.message("payment 결제취소 성공")
    					.build();
		    }
    	    
		} else {
    		log.info("[결제] 결제취소 정보 인증실패 message;{}",response.get("message"));
    		return Response
    	    		.<Boolean>builder()
    	    		.code(HttpStatusCode.FAIL.getCodeValue())
    	    		.message((String)response.get("message"))
    	    		.build();
		}
	}
	
	public Response<Boolean> updatePaymentCancel(Long orderId, String reason, int amount) {
		ObjectMapper snakeObjectMapper = new ObjectMapperFactory().getSnakeObjectMapper();
		Payment payment = paymentBO.getPaymentByOrderId(orderId);		
		if (payment == null) {
			log.info("[결제] 결제취소 실패 결제 내역이 없음 orderId:{}",orderId);
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("결제확인 실패")
					.data(false)
					.build();
		}
		PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
		portoneToken = validateAndGetPortoneToken(portoneToken);
		Map<String, Object> response = paymentAutoBO.getPaymentCancel(payment.getImpUid(), reason, amount, portoneToken.getAccessToken()).block();
		if (response != null && (int)response.get("code") == 0) {
			Map<String, Object> responseData = (Map<String, Object>) response.get("response");
			Payment newPayment = snakeObjectMapper.convertValue(responseData, Payment.class);
			int result = paymentBO.updatePayment(newPayment
	    	    		.toBuilder()
	    	    		.orderId(payment.getOrderId())
	    	    		.userId(payment.getUserId())
	    	    		.build());
		    if (result != 1) {
		    	log.info("[결제] 결제취소 결제 업데이트 실패 impUid:{}",payment.getImpUid());
		    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.FAIL.getCodeValue())
    					.message("payment 결제취소 실패")
    					.build();
		    } else {
		    	Order order = orderBO.getOrderById(orderId).getData();
		    	if (order != null) {
		    		orderBO.updateOrderStatus(orderId, order.getUserId(), "취소");
		    	} else {
		    		log.info("[결제] 결제취소 order가 없음 orderId:{}", orderId);
			    	return Response
	    					.<Boolean>builder()
	    					.code(HttpStatusCode.FAIL.getCodeValue())
	    					.message("payment 결제취소 성공 결제정보 업데이트 실패")
	    					.build();
		    	}
		    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.OK.getCodeValue())
    					.message("payment 결제취소 성공")
    					.build();
		    }
		} else if ( response != null && response.get("message").equals("결제취소에 실패하였습니다. [500626|기 취소 거래]")) {
			int result = paymentBO.updatePayment(payment
	    	    		.toBuilder()
	    	    		.orderId(payment.getOrderId())
	    	    		.userId(payment.getUserId())
	    	    		.status("cancel")
	    	    		.cancelAmount(amount)
	    	    		.cancelReason(reason)
	    	    		.cancelledAt(LocalDateTime.now())
	    	    		.build());
		    if (result != 1) {
		    	log.info("[결제] 결제취소 결제 업데이트 실패 impUid:{}",payment.getImpUid());
		    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.FAIL.getCodeValue())
    					.message("payment 결제취소 실패")
    					.build();
		    } else {
		    	Order order = orderBO.getOrderById(orderId).getData();
		    	if (order != null) {
		    		orderBO.updateOrderStatus(orderId, order.getUserId(), "취소");
		    	} else {
		    		log.info("[결제] 결제취소 order가 없음 orderId:{}", orderId);
			    	return Response
	    					.<Boolean>builder()
	    					.code(HttpStatusCode.FAIL.getCodeValue())
	    					.message("payment 결제취소 성공 결제정보 업데이트 실패")
	    					.build();
		    	}
		    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.OK.getCodeValue())
    					.message("payment 결제취소 성공")
    					.build();
		    }
    	    
		} else {
    		log.info("[결제] 결제취소 정보 인증실패 message;{}",response.get("message"));
    		return Response
    	    		.<Boolean>builder()
    	    		.code(HttpStatusCode.FAIL.getCodeValue())
    	    		.message((String)response.get("message"))
    	    		.build();
		}
	}
	
	public PortoneToken validateAndGetPortoneToken(PortoneToken protoneToken) {
		if (protoneToken != null) {
			if (protoneToken.getExpiredAt().isAfter(LocalDateTime.now())) {
				return protoneToken;
			} else {
				return paymentAutoBO.updatePortoneToken();
			}
		} 

	    return paymentAutoBO.getPortoneToken();
	}
	
}
