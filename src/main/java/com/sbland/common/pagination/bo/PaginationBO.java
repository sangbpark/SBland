package com.sbland.common.pagination.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sbland.common.pagination.dto.PageDTO;
import com.sbland.common.pagination.dto.PaginationDTO;
import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaginationBO {
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;
	
	public List<PageDTO> getPageList(int count, Integer code, Integer rightValue, String keyword, String plusKeyword) {
		List<PageDTO> pageList = new ArrayList<>();
		int size = productThumbnailCardDTOBO.getProductThumbnailCardDTOSizeBySearch(code, rightValue, plusKeyword);
		if (size == 0) return pageList;
		int pageCount = (int)Math.ceil((double)size / count);
		if (pageCount == 0 || pageCount == 1) return pageList;
		for (int i = 1; i <= pageCount; i++) {
			PageDTO pageDTO = PageDTO
					.builder()
					.page(i)
					.pageProductCount(count)
					.code(code)
					.rightValue(rightValue)
					.keyword(keyword)
					.build();
			pageList.add(pageDTO);					
		}
		return pageList;
	}
	
	public PaginationDTO getProductThumbnailPaging(Integer page, Integer code, Integer rightValue, String keyword, int count) {
		String plusKeyword = "";
		if (keyword.length() >= 3) {
			plusKeyword = Arrays
					.stream(keyword.split(" "))
					.map(key -> "+" + key)
					.collect(Collectors.joining(" "));
		}
		List<PageDTO> pageDTOList = getPageList(count, code, rightValue, keyword, plusKeyword);
		Integer offset = (page - 1) * count;	
		if (pageDTOList.size() > 0) {
			if (pageDTOList.size() < page) {
				PageDTO nowPageDTO = pageDTOList.get(pageDTOList.size() - 1);
				List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOBySearch(nowPageDTO.getCode(), nowPageDTO.getRightValue(), plusKeyword, nowPageDTO.getPageProductCount(), offset);
				return getPaginationDTO(productThumbnailCardDTOList, pageDTOList, nowPageDTO);
			} 
			List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOBySearch(code, rightValue, plusKeyword, count, offset);
			PageDTO nowPageDTO = pageDTOList.get(page - 1);
			return getPaginationDTO(productThumbnailCardDTOList, pageDTOList, nowPageDTO);
		}
		
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOBySearch(code, rightValue, plusKeyword, count, offset);
		PageDTO nowPageDTO = PageDTO
				.builder()
				.page(page)
				.code(code)
				.pageProductCount(count)
				.rightValue(rightValue)
				.build();			
		return  getPaginationDTO(productThumbnailCardDTOList, pageDTOList, nowPageDTO);
	}


	public PaginationDTO getPaginationDTO(List<ProductThumbnailCardDTO> productThumbnailCardDTOList, List<PageDTO> pageDTOList, PageDTO nowPageDTO) {		
		if(pageDTOList.size() > 5 ) {
			int offset = (nowPageDTO.getPage() / 5);
			if (nowPageDTO.getPage() % 5 == 0) {
				offset--;
			}
			int startPage = 5 * offset;
			int endPage = startPage + 5;
			List<PageDTO> pageDTOLimit5List = pageDTOList
					.stream()
					.filter(page -> page.getPage() > startPage
								&&  page.getPage() <= endPage
					)
					.collect(Collectors.toList());
			return PaginationDTO.builder()
					.pageDTOList(pageDTOLimit5List)
					.ProductThumbnailCardDTOList(productThumbnailCardDTOList)
					.nowPageDTO(nowPageDTO)
					.maxSize(pageDTOList.size())
					.build();
					
		}
		
		return PaginationDTO.builder()
				.pageDTOList(pageDTOList)
				.ProductThumbnailCardDTOList(productThumbnailCardDTOList)
				.nowPageDTO(nowPageDTO)
				.maxSize(pageDTOList.size())
				.build();
	}
}
