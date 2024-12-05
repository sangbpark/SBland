package com.sbland.common.pagination.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sbland.common.pagination.dto.PageDTO;
import com.sbland.common.pagination.dto.PaginationDTO;
import com.sbland.order.bo.OrderBO;
import com.sbland.order.bo.OrderServiceBO;
import com.sbland.order.dto.OrderDTO;
import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaginationBO {
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;
	private final OrderBO orderBO;
	private final OrderServiceBO orderServiceBO;
	
	private List<PageDTO> getPageList(int count, Integer code, Integer rightValue, String keyword, String plusKeyword) {
		List<PageDTO> pageList = new ArrayList<>();
		int size = productThumbnailCardDTOBO.getProductThumbnailCardDTOSizeBySearch(code, rightValue, plusKeyword);
		if (size == 0) return pageList;
		int pageCount = (int)Math.ceil((double)size / count);
		if (pageCount == 0 || pageCount == 1) return pageList;
		for (int i = 1; i <= pageCount; i++) {
			PageDTO pageDTO = PageDTO
					.builder()
					.page(i)
					.pageItemsCount(count)
					.code(code)
					.rightValue(rightValue)
					.keyword(keyword)
					.build();
			pageList.add(pageDTO);					
		}
		return pageList;
	}
	
	private List<PageDTO> getPageList(Long userId, int count) {
		List<PageDTO> pageList = new ArrayList<>();
		int size = orderBO.getOrderListSizeByUserId(userId);
		if (size == 0) return pageList;
		int pageCount = (int)Math.ceil((double)size / count);
		if (pageCount == 0 || pageCount == 1) return pageList;
		for (int i = 1; i <= pageCount; i++) {
			PageDTO pageDTO = PageDTO
					.builder()
					.page(i)
					.pageItemsCount(count)
					.build();
			pageList.add(pageDTO);					
		}
		return pageList;
	}
	
	public PaginationDTO<ProductThumbnailCardDTO> getPaging(Integer page, Integer code, Integer rightValue, String keyword, int count) {
		String plusKeyword = "";
		Set<String> stopword = new HashSet<>( List.of("a", "about", "an", "are", "as", "at", "be", "by"
				, "com", "de", "en", "for", "from", "how", "i", "in", "is", "it", "la", "of"
				, "on", "or", "that", "the", "this", "to", "was", "what", "when", "where"
				, "who", "will", "with", "und", "the", "www"));
		if (keyword.length() >= 3) {
			plusKeyword = Arrays
					.stream(keyword.split("\\s+"))
					.filter(key -> !stopword.contains(key))
					.map(key -> "+" + key + "*")
					.collect(Collectors.joining(" "));
		}
		List<PageDTO> pageDTOList = getPageList(count, code, rightValue, keyword, plusKeyword);
		Integer offset = (page - 1) * count;	
		if (pageDTOList.size() > 0) {
			if (pageDTOList.size() < page) {
				PageDTO nowPageDTO = pageDTOList.get(pageDTOList.size() - 1);
				List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOBySearch(nowPageDTO.getCode(), nowPageDTO.getRightValue(), plusKeyword, nowPageDTO.getPageItemsCount(), offset);
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
				.pageItemsCount(count)
				.rightValue(rightValue)
				.build();			
		return  getPaginationDTO(productThumbnailCardDTOList, pageDTOList, nowPageDTO);
	}
	
	public PaginationDTO<OrderDTO> getPaging(Long userId, Integer page, int count) {
		List<PageDTO> pageDTOList = getPageList(userId, count);
		Integer offset = (page - 1) * count;	
		if (pageDTOList.size() > 0) {
			if (pageDTOList.size() < page) {
				PageDTO nowPageDTO = pageDTOList.get(pageDTOList.size() - 1);
				List<OrderDTO> orderDTOList = orderServiceBO.getResponseOrderDTOListByUserId(userId, count, offset).getData();
				return getPaginationDTO(orderDTOList, pageDTOList, nowPageDTO);
			} 
			List<OrderDTO> orderDTOList = orderServiceBO.getResponseOrderDTOListByUserId(userId, count, offset).getData();
			PageDTO nowPageDTO = pageDTOList.get(page - 1);
			return getPaginationDTO(orderDTOList, pageDTOList, nowPageDTO);
		}
		
		List<OrderDTO> orderDTOList = orderServiceBO.getResponseOrderDTOListByUserId(userId, count, offset).getData();
		PageDTO nowPageDTO = PageDTO
				.builder()
				.page(page)
				.pageItemsCount(count)			
				.build();			
		return  getPaginationDTO(orderDTOList, pageDTOList, nowPageDTO);
	}

	private <T> PaginationDTO<T> getPaginationDTO(List<T> pageItemsList, List<PageDTO> pageDTOList, PageDTO nowPageDTO) {		
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
			return PaginationDTO
					.<T>builder()
					.pageDTOList(pageDTOLimit5List)
					.paginationItems(pageItemsList)
					.nowPageDTO(nowPageDTO)
					.maxSize(pageDTOList.size())
					.build();
					
		}
		
		return PaginationDTO
				.<T>builder()
				.pageDTOList(pageDTOList)
				.paginationItems(pageItemsList)
				.nowPageDTO(nowPageDTO)
				.maxSize(pageDTOList.size())
				.build();
	}
	
}
