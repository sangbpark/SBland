package com.sbland.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.category.bo.CategoryBO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/category")
@RequiredArgsConstructor
@RestController
public class CategoryRestController {
	private final CategoryBO categoryBO;
	
}
