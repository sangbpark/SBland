package com.sbland.product.domain;

import java.time.LocalDateTime;

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
public class EbayProductImageDTO {
	private Long product_id;
	private String product_name;
	private String url;
	private Boolean is_thumbnail;
	private int position;
}
