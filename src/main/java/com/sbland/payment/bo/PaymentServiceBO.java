package com.sbland.payment.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.common.uid.UidGenerator;
import com.sbland.order.bo.OrderServiceBO;
import com.sbland.payment.domain.Payment;
import com.sbland.payment.dto.PortoneToken;
import com.sbland.product.bo.ProductStockServiceBO;
import com.sbland.product.dto.ProductStockDTO;

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
	private final UidGenerator uidGenerator;
	
	@Cacheable(value = "portoneTokens", key = "'portoneToken'")
	public PortoneToken getPortoneToken() {
		Map<String, Object> tokenMap = paymentAutoBO.getAccessToken().block(); 
	    return PortoneToken.builder()
	            .accessToken((String)tokenMap.get("accessToken"))
	            .expiredAt((LocalDateTime)tokenMap.get("expired_at"))
	            .now((LocalDateTime)tokenMap.get("now"))
	            .build();
	}
		
    public Response<Boolean> verifyPayment(String impUid, Long userId, int deliveryfee, String orderDetailMapListJson, String shippinAddress)  {   	
    	List<Map<String, Object>> orderDetailMapList;
		try {
			orderDetailMapList = objectMapper.readValue(orderDetailMapListJson, new TypeReference<>() {});
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[결제] 상품 디테일 리스트 파싱 실패 ");
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("결제 정보 받기 실패")
					.build();
		}
    	PortoneToken portoneToken = getPortoneToken();
    	Map<String, Object> response = paymentAutoBO.getVerify(impUid, portoneToken.getAccessToken()).block();
    	if (response != null && (int) response.get("code") == 0) {
    		boolean productStockResult = productStockServiceBO.updateProductStock(orderDetailMapList
    				.stream()
    				.map(orderDetailMap -> objectMapper.convertValue(orderDetailMap, ProductStockDTO.class))
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
    	    int result = paymentBO.addPayment(payment);
    	    if (result != 1) {
    	    	return Response
    					.<Boolean>builder()
    					.code(HttpStatusCode.FAIL.getCodeValue())
    					.message("payment 정보 저장 실패")
    					.build();
    	    }
    	    return orderServiceBO.addOrderAndOrderDetail(userId, payment.getMerchantUid(), payment.getAmount(), deliveryfee, "결제완료", shippinAddress, orderDetailMapList);
    	} else {
    		log.info("[결제] 결제 정보 인증실패 message;{}",response.get("message"));
    		return Response
    	    		.<Boolean>builder()
    	    		.code(HttpStatusCode.FAIL.getCodeValue())
    	    		.message((String)response.get("message"))
    	    		.build();
    	}
    }
}
