package com.sbland.common;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sbland.category.bo.CategoryBO;
import com.sbland.category.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {
	private final CategoryBO categoryBO;


	   @ModelAttribute
	    public void categoryList(Model model) {
		   List<CategoryDTO> categoryDTOListTree = categoryBO.getCategoryMenu();

	        model.addAttribute("categoryDTOListTree", categoryDTOListTree);
	    }
}
