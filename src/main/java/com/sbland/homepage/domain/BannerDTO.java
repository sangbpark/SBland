package com.sbland.homepage.domain;

import org.springframework.web.multipart.MultipartFile;

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
public class BannerDTO {
	private String title;
	private int positon;
	private int categoryCode;
	private MultipartFile bannerImage;
}
