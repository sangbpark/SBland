package com.sbland.common.pagination.dto;

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
public class PageDTO {
	private int page;
	private int pageItemsCount;
	private Integer code;
	private Integer rightValue;
	private String keyword;
}
