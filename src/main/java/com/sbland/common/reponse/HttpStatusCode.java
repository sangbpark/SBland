package com.sbland.common.reponse;

import lombok.Getter;


@Getter
public enum HttpStatusCode {
	OK(200),
	FAIL(500),
	FORBIDDEN(403),
	NONMEMBER(300);
	
	private final int codeValue;
	HttpStatusCode(int i) {
		this.codeValue = i;
	}
}
