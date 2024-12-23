package com.sbland.aop.payment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.exception.PaymentException;
import com.sbland.payment.bo.PaymentAutoBO;
import com.sbland.payment.bo.PaymentServiceBO;
import com.sbland.payment.dto.PortoneToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PaymentTransactionalAspect {
	private final TransactionTemplate transactionTemplate;
	private final PaymentAutoBO paymentAutoBO;
	private final PaymentServiceBO paymentServiceBO;
    
	@Around("@annotation(PaymentTransactional)")
    public Object handlePaymentTransactional(ProceedingJoinPoint joinPoint) throws Throwable {
	//
		return transactionTemplate.execute(status -> {
			try {
				return joinPoint.proceed();
			} catch(PaymentException e) {
				status.setRollbackOnly();
				if (e.getMessage().startsWith("404 Not Found from GET")) {
					return Response
							.<Boolean>builder()
							.code(HttpStatusCode.FAIL.getCodeValue())
							.message("결제내역이 없습니다.")
							.data(false)
							.build();
				}
				PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
				portoneToken = paymentServiceBO.validateAndGetPortoneToken(portoneToken);
				paymentAutoBO.getPaymentCancel(e.getImpUid(), "쇼핑몰 오류", 0, portoneToken.getAccessToken()).block();
				return Response
						.<Boolean>builder()
						.code(HttpStatusCode.FAIL.getCodeValue())
						.message("결제를 실패했습니다.")
						.data(false)
						.build();
			} catch (Throwable t) {
				log.debug("[결제] 결제중 예상못한 예외 발생 error:{}", t.getMessage());
	            throw new RuntimeException("Unhandled exception occurred", t);
	        }
		});
    }
}
