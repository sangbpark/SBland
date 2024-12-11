package com.sbland.payment.bo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.oderdetail.dto.OrderDetailPaymentDTO;
import com.sbland.order.bo.OrderServiceBO;
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
	private final OrderServiceBO orderServiceBO;
	private final PaymentAutoBO paymentAutoBO;
	private final ObjectMapper objectMapper;
	private final ProductStockServiceBO productStockServiceBO;
	private final ShoppingcartBO shoppingcartBO;
	
	@Transactional
    public Response<Boolean> verifyPayment(String impUid, Long userId, int deliveryfee, String shippinAddress, List<OrderDetailPaymentDTO> orderDetailPaymentDTOList)  {   	
    	PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
    	portoneToken = validateAndGetPortoneToken(portoneToken);
    	Map<String, Object> response = paymentAutoBO.getVerify(impUid, portoneToken.getAccessToken()).block();
    	if (response != null && (int) response.get("code") == 0) {
    		boolean productStockResult = productStockServiceBO.updateProductStock(orderDetailPaymentDTOList
    				.stream()
    				.map(orderDetailPaymentDTO -> objectMapper.convertValue(orderDetailPaymentDTO, ProductStockDTO.class))
    				.collect(Collectors.toList())
    				);
    		if (!productStockResult) {
    			return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.FAIL.getCodeValue())
    					.message("상품재고 업데이트 실패")
    					.build();
    		}
    		
    	    Map<String, Object> paymentData = (Map<String, Object>) response.get("response");
    	    Payment payment = objectMapper.convertValue(paymentData, Payment.class);
    	    Response<Long> orderResponse = orderServiceBO.addOrderAndOrderDetail(userId, payment.getMerchantUid(), payment.getAmount(), deliveryfee, "결제완료", shippinAddress, orderDetailPaymentDTOList);
    	    int result = paymentBO.addPayment(payment
    	    		.toBuilder()
    	    		.orderId(orderResponse.getData())
    	    		.userId(userId)
    	    		.build());
    	    if (result != 1) {
    	    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.FAIL.getCodeValue())
    					.message("payment 정보 저장 실패")
    					.build();
    	    } else {
    	    	int shoppingcartResult = shoppingcartBO.deleteShoppingcartByUserIdAndProductIdList(userId ,orderDetailPaymentDTOList
    	    			.stream()
    	    			.map(orderDetailPaymentDTO -> orderDetailPaymentDTO.getProductId())
    	    			.collect(Collectors.toList()));
    	    	if (shoppingcartResult > 0) {
	    	    	return Response
	    					.<Boolean>builder()
	    					.code(HttpStatusCode.OK.getCodeValue())
	    					.message("성공")
	    					.data(true)
	    					.build();
    	    	} else {
    	    		return Response
	    					.<Boolean>builder()
	    					.code(HttpStatusCode.FAIL.getCodeValue())
	    					.message("실패")
	    					.data(false)
	    					.build();
    	    	}
    	    }
    	} else {
    		log.info("[결제] 결제 정보 인증실패 message;{}",response.get("message"));
    		return Response
    	    		.<Boolean>builder()
    	    		.code(HttpStatusCode.FAIL.getCodeValue())
    	    		.message((String)response.get("message"))
    	    		.build();
    	}
    }
	
	public Response<Boolean> workPaymentCancel(String impUid, String reason, int amount) {
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
		Map<String, Object> response = paymentAutoBO.getPaymentCancel(impUid, payment.getMerchantUid(), reason, amount, portoneToken.getAccessToken()).block();
		if ( response != null && response.get("message").equals("결제취소에 실패하였습니다. [500626|기 취소 거래]")) {
			Map<String, Object> responseData = (Map<String, Object>) response.get("response");
//			Payment newPayment = objectMapper.convertValue(responseData, Payment.class);
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
