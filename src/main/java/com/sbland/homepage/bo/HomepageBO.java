package com.sbland.homepage.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.sbland.aop.cache.FindCache;
import com.sbland.homepage.domain.Banner;

@Service
public class HomepageBO {
	
	@FindCache(value="Homepage", key = "'Banner'")
	public List<Banner> getHomepageBanner() {
		return  null;
	}
	
	@CachePut(value = "Homepage", key = "'Banner'")
	public List<Banner> addHomepageBanner(List<Banner> banner) {		
		return banner;
	}
	
	@FindCache(value="Homepage", key = "'mdProduct'")
	public List<Long> getHomepageMdProduct() {
		return new ArrayList<>();
	}
	
	@CachePut(value = "Homepage", key = "'mdProduct'")
	public List<Long> updateHomepageMdProduct(List<Long> mdProductIdList) {		
		return mdProductIdList;
	}
}
