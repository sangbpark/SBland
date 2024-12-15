package com.sbland.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.Response;
import com.sbland.order.bo.OrderBO;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/order")
@RestController
public class OrderRestController {
	private final OrderBO orderBO;

	@GetMapping("/update-cancel-wait")
	public Response<Boolean> updateOrder(
			@RequestParam("orderId") Long orderId,
			HttpSession session) {
		UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("userSession");
		return orderBO.updateOrderStatus(orderId, userSession.getId(), "취소대기");
	}
}
