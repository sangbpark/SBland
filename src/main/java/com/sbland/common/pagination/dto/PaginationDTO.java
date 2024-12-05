package com.sbland.common.pagination.dto;

import java.util.List;

import com.sbland.product.dto.ProductThumbnailCardDTO;

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
public class PaginationDTO<T> {
	private List<PageDTO> pageDTOList;
	private List<T> paginationItems;
	private PageDTO nowPageDTO;
	private Integer maxSize;
	
}
