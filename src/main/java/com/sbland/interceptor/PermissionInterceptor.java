package com.sbland.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.objectmapper.ObjectMapperFactory;
import com.sbland.common.reponse.Response;
import com.sbland.payment.dto.PaymentRequestDTO;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler){
		
		String uri = request.getRequestURI();
		
		HttpSession session = request.getSession();
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		
		if (uri.startsWith("/payment") && userSession == null) {
			ObjectMapper objectMapper = new ObjectMapperFactory().getCamelObjectMapper();
			PaymentRequestDTO paymentRequestDTO = PaymentRequestDTO.builder().build();
		
			try {
				paymentRequestDTO = objectMapper.readValue(request.getInputStream(), PaymentRequestDTO.class);
			} catch (IOException e) {
				log.error("[preHandle] payment 리퀘스트 에러 error:{}",e.getMessage());;
				try {
					response.sendRedirect("/");
				} catch (IOException e1) {
					log.info("[preHandle] payment리다이렉트 실패 error:{}",e1.getMessage());
				}
				return false;
			}
				
			log.warn("[preHandle] login없이 payment에 들어옴 paymentRequestDTO:{}", paymentRequestDTO);	
			return true;
		}
		
		if (uri.startsWith("/shoppingcart") && userSession == null) {
			try {
				response.sendRedirect("/user/user-in-view");
			} catch (IOException e) {
				log.info("[preHandle] user리다이렉트 실패 error:{}",e.getMessage());
			} 
			return false;
		}
		
		
		if (uri.startsWith("/user/protect") && userSession == null) {
			try {
				response.sendRedirect("/user/user-in-view");				
			} catch (IOException e) {
				log.info("[preHandle] user리다이렉트 실패 error:{}",e.getMessage());
			}
			
			return false;
		}
		
		if (uri.startsWith("/user/user-in-view") && userSession != null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				log.info("[preHandle] user리다이렉트 실패 error:{}",e.getMessage());
			}			
			return false;
		}

		if (uri.startsWith("/user/user-up-view") && userSession != null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				log.info("[preHandle] user리다이렉트 실패 error:{}",e.getMessage());
			}			
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView mav) {

	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			Exception ex) {
	}

}