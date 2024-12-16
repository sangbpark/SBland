package com.sbland.homepage.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbland.common.file.FileManager;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.homepage.domain.Banner;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HomepageServiceBO {
	private final HomepageBO homepageBannerBO;
	private final FileManager fileManager;
	
	public List<Banner> getBannerList() {
		return homepageBannerBO.getHomepageBanner();
	}
	
	public Response<Boolean> updateMdProductId(List<Long> idList) {
		homepageBannerBO.updateHomepageMdProduct(idList);
		return Response
				.<Boolean>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("성공")
				.data(true)
				.build();
	}
	
	public Response<Boolean> updateBanner(String title, int position, int categoryCode, MultipartFile bannerImage) {
		List<Banner> nowBannerList = getBannerList();
		if (nowBannerList == null ) {
			nowBannerList = new ArrayList<>(List.of(Banner.builder().build(), Banner.builder().build(), Banner.builder().build()));
		}
		Banner newBanner = Banner
				.builder()
				.title(title)
				.positon(position)
				.categoryCode(categoryCode)
				.imageUrl(fileManager.uploadFile(bannerImage
						, title, "banner")
				)
				.build();
		if ( nowBannerList.get(position - 1).getPositon() != null) {
			fileManager.deleteFile(nowBannerList.get(position - 1).getImageUrl());
		}
		nowBannerList.set(position - 1, newBanner);
		homepageBannerBO.addHomepageBanner(nowBannerList);
		return Response
				.<Boolean>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("성공")
				.data(true)
				.build();
	}
}
