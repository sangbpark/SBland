package com.sbland.homepage.domain;

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
public class Banner {
	private String title;
	private Integer positon;
	private int categoryCode;
	private String imageUrl;
}
