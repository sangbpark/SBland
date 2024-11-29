package com.sbland;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbland.ebay.bo.EbayDataBO;
import com.sbland.exrate.bo.ExRateBO;
import com.sbland.product.bo.ProductBO;
import com.sbland.product.bo.ProductImageBO;
import com.sbland.product.domain.EbayProduct;
import com.sbland.product.domain.EbayProductImageDTO;

import reactor.core.publisher.Flux;

@Controller
public class testController {
	@Autowired
	private EbayDataBO es;
	
	@Autowired
	private ExRateBO exRateBO;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductImageBO productImageBO;

	@RequestMapping("/test")
	public String test() {
		return "homepage/homepage";
	}
	
	@RequestMapping("/test2")
	public String test2() {
		return "user/signIn";
	}
	
	@RequestMapping("/test3")
	public String test3() {
		return "user/signUp";
	}
	
	@RequestMapping("/test4")
	public String test4() {
		return "product/productList";
	}
	
	@RequestMapping("/test5")
	public String test5() {
		return "user/shoppingCart";
	}
	
	@RequestMapping("/test6")
	public String test6() {
		return "user/myPage";
	}
	
	@ResponseBody
	@RequestMapping("/test7")
	public void test7() {
		for (int i = 100; i <= 10000; i += 100) {
			List<EbayProduct> ebayProductList = es.getItems("warhammer", i)
		        .flatMapMany(Flux::fromIterable)
		        .collectList()
		        .block(); 
			for (EbayProduct temp : ebayProductList) {
				Long id = productBO.addProduct((String)temp.title()
						, null, exRateBO.calculateExRate(new BigDecimal ((String)temp.price().get("value"))
						, (String) temp.price().get("currency")), "판매중", null);
				List<EbayProductImageDTO> ebayProductImageDTOList = new ArrayList<>();
				EbayProductImageDTO epd = EbayProductImageDTO
										.builder()
										.product_id(id)
										.is_thumbnail(true)
										.position(0)
										.product_name((String)temp.title())
										.url((String)temp.thumbnailImages().get(0).get("imageUrl"))
										.build();
				ebayProductImageDTOList.add(epd);
				EbayProductImageDTO epd2 = EbayProductImageDTO
										.builder()
										.product_id(id)
										.is_thumbnail(false)
										.position(1)
										.product_name((String)temp.title())
										.url((String)temp.image().get("imageUrl"))
										.build();
				ebayProductImageDTOList.add(epd2);
				productImageBO.addProductImage(ebayProductImageDTOList);
			}
		}
	}
}
