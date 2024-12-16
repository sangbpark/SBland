package com.sbland.homepage;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbland.common.reponse.Response;
import com.sbland.homepage.bo.HomepageServiceBO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/homepage")
@RequiredArgsConstructor
@RestController
public class HomepageAdminRestController {
	private final HomepageServiceBO homepageServiceBO;
	
	@PostMapping("/banner-update")
	public Response<Boolean> updateBanner(
			@RequestParam("title") String title,
			@RequestParam("position") int position,
			@RequestParam("categoryCode") int categoryCode,
			@RequestParam("bannerImage") MultipartFile bannerImage) {
		return homepageServiceBO.updateBanner(title, position, categoryCode,bannerImage);
	}
}
