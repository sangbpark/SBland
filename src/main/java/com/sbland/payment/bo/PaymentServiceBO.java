package com.sbland.payment.bo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.common.uid.UidGenerator;
import com.sbland.oderdetail.dto.OrderDetailPaymentDTO;
import com.sbland.order.bo.OrderServiceBO;
import com.sbland.payment.domain.Payment;
import com.sbland.payment.dto.PortoneToken;
import com.sbland.product.bo.ProductStockServiceBO;
import com.sbland.product.dto.ProductStockDTO;
import com.sbland.shoppingcart.bo.ShoppingcartServiceBO;

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
	private final ShoppingcartServiceBO shoppingcartServiceBO;
	private final UidGenerator uidGenerator;
	
	@Cacheable(value = "portoneTokens", key = "'portoneToken'")
	public PortoneToken getPortoneToken() {
		Map<String, Object> responseData = paymentAutoBO.getAccessToken().block();
		Map<String, Object> tokenMap =(Map<String, Object>) responseData.get("response");
	    return PortoneToken
	    		.builder()
	    		.accessToken((String)tokenMap.get("access_token"))
	    		.expiredAt(LocalDateTime.ofInstant(
	    			        Instant.ofEpochSecond((Integer) tokenMap.get("expired_at")),
	    			        ZoneId.of("Asia/Seoul"))
	    		)
	    		.now(LocalDateTime.ofInstant(
	    			        Instant.ofEpochSecond((Integer) tokenMap.get("now")),
	    			        ZoneId.of("Asia/Seoul"))
	    		)
	    		.build();
	}
	
	@Transactional
    public Response<Boolean> verifyPayment(String impUid, Long userId, int deliveryfee, String shippinAddress, List<OrderDetailPaymentDTO> orderDetailPaymentDTOList)  {   	
    	PortoneToken portoneToken = getPortoneToken();
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
    	    		.paidAt(LocalDateTime.ofInstant(
    	    	            Instant.ofEpochSecond(Long.parseLong(payment.getPaidAt())),
    	    	            ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
	    			 )
    	    		.build());
    	    if (result != 1) {
    	    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.FAIL.getCodeValue())
    					.message("payment 정보 저장 실패")
    					.build();
    	    } else {
    	    	Response<Boolean> scResponse = shoppingcartServiceBO.deleteShoppingcartByUserId(userId);
    	    	if (scResponse.getData()) {
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
	
	public PortoneToken validateAndGetPortoneToken(PortoneToken protoneToken) {
		if (protoneToken != null) {
			if (protoneToken.getExpiredAt().isAfter(LocalDateTime.now())) {
				return protoneToken;
			} else {
				return paymentAutoBO.updatePortoneToken();
			}
		} 

	    return getPortoneToken();
	}
	
}
