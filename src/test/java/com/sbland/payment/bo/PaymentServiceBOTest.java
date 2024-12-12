package com.sbland.payment.bo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.oderdetail.dto.OrderDetailDTO;
import com.sbland.oderdetail.dto.OrderDetailPaymentDTO;
import com.sbland.payment.PaymentRestController;
import com.sbland.payment.dto.PortoneToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@EnableCaching
class PaymentServiceBOTest {
	@Autowired
	PaymentAutoBO paymentAutoBO;
	@Autowired
	PaymentServiceBO paymentServiceBO;
	@Autowired
	PaymentRestController paymentRestController;

	@Test
	void 캐시테스트() {
		paymentAutoBO.getPortoneToken();															
		PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
		portoneToken = paymentServiceBO.validateAndGetPortoneToken(portoneToken);
		PortoneToken newPortoneToke = paymentServiceBO.validateAndGetPortoneToken(portoneToken);
		log.info("[테스트] portoneToken:{}, new:{}", portoneToken, newPortoneToke);
	}
	
	@Transactional
	@Test
	void 결제취소테스트() {
		PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
		portoneToken = paymentServiceBO.validateAndGetPortoneToken(portoneToken);
		paymentServiceBO.workPaymentCancel("imp_339482206636", "테스트", 0);
	}
	
	@Test
	void 결제중오류테스트() {
		List<OrderDetailPaymentDTO> orderDetailPaymentDTOList = new ArrayList<>();
		OrderDetailPaymentDTO orderDetailPaymentDTO = OrderDetailPaymentDTO
				.builder()
				.productCount(3)
				.productId(19L)
				.productPrice(0)
				.totalPrice(0)
				.build();
		orderDetailPaymentDTOList.add(orderDetailPaymentDTO);
		paymentServiceBO.addPaymentflow("imp_339482206636", 4L, 0, "결제테스트", orderDetailPaymentDTOList);
	}
	
}
