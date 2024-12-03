package com.sbland.category.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder(toBuilder=true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDTO {
	private String name;
	private int code;
	@Column(name ="right_value")
	private int rightValue;
	private int depth;
	@Builder.Default
	private List<CategoryDTO> childCategory = new ArrayList<>();
	
	public void addChildList(CategoryDTO childCategory) {
		this.childCategory.add(childCategory);
	}
}
