package com.sbland.order;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbland.common.pagination.bo.PaginationBO;
import com.sbland.common.pagination.dto.PaginationDTO;
import com.sbland.order.dto.OrderDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OrderAdminController {
	private final PaginationBO paginationBO;
	
	@GetMapping("/admin/order/payment-view")
	public String myPage(
			@RequestParam("page") Optional<Integer> page,
			Model model) {
		
		PaginationDTO<OrderDTO> paginationDTO = paginationBO.getPaging("취소대기", page.orElse(1), 5 );
		model.addAttribute("pageList", paginationDTO.getPageDTOList());
		model.addAttribute("orderDTOList", paginationDTO.getPaginationItems());
		model.addAttribute("nowPage", paginationDTO.getNowPageDTO());
		model.addAttribute("maxSize", paginationDTO.getMaxSize());
		return "admin/adminPayment";
	}
}
