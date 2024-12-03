package com.sbland.common.pagination.bo;

import java.util.ArrayList;
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
	
	public List<PageDTO> getPageList(int count, Integer code, Integer rightValue, String keyword) {
		List<PageDTO> pageList = new ArrayList<>();
		int size = productThumbnailCardDTOBO.getProductThumbnailCardDTOSizeBySearch(code, rightValue, keyword);
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
		if (keyword != null && keyword.length() < 3) keyword=null;
		List<PageDTO> pageDTOList = getPageList(count, code, rightValue, keyword);
		Integer offset = null;
		if(page != null) {
			offset = (page - 1) * count;
		}
		if (page != null) {
			if (pageDTOList.size() < page) {
				PageDTO pageDTO = pageDTOList.get(pageDTOList.size() - 1);
				List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOBySearch(pageDTO.getCode(), pageDTO.getRightValue(), pageDTO.getKeyword(), pageDTO.getPageProductCount(), offset);
				return getPaginationDTO(productThumbnailCardDTOList, pageDTOList, pageDTO);
			} 
			List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOBySearch(code, rightValue, keyword, count, offset);
			PageDTO pageDTO = pageDTOList.get(page - 1);
			return getPaginationDTO(productThumbnailCardDTOList, pageDTOList, pageDTO);
		}
		
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOBySearch(code, rightValue, keyword, count, offset);
		PageDTO pageDTO = PageDTO
				.builder()
				.page(1)
				.code(code)
				.pageProductCount(count)
				.rightValue(rightValue)
				.build();			
		return  getPaginationDTO(productThumbnailCardDTOList, pageDTOList, pageDTO);
	}


	public PaginationDTO getPaginationDTO(List<ProductThumbnailCardDTO> productThumbnailCardDTOList, List<PageDTO> pageDTOList, PageDTO pageDTO) {		
		if(pageDTOList.size() > 5 ) {
			int offset = (pageDTO.getPage() / 5);
			if (pageDTO.getPage() % 5 == 0) {
				offset--;
			}
			int startPage = (5 * offset) + 1;
			int endPage = startPage + 4;
			List<PageDTO> pageDTOLimit5List = pageDTOList
					.stream()
					.filter(page -> page.getPage() >= startPage
								&&  page.getPage() <= endPage
					)
					.collect(Collectors.toList());
			return PaginationDTO.builder()
					.pageDTOList(pageDTOLimit5List)
					.ProductThumbnailCardDTOList(productThumbnailCardDTOList)
					.nowPageDTO(pageDTO)
					.maxSize(pageDTOList.size())
					.build();
					
		}
		
		return PaginationDTO.builder()
				.pageDTOList(pageDTOList)
				.ProductThumbnailCardDTOList(productThumbnailCardDTOList)
				.nowPageDTO(pageDTO)
				.maxSize(pageDTOList.size())
				.build();
	}
}
