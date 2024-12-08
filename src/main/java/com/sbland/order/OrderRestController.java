package com.sbland.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.order.bo.OrderServiceBO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/order")
@RestController
public class OrderRestController {
	private OrderServiceBO orderServiceBO;


}
