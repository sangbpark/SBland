package com.sbland.common.encrypt;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EncryptUtils {
	private final EncryptInf encryptInf;

	public String hashedPassword(String password) {
		return encryptInf.hashedPassword(password);
	}
	
	public boolean isMatchPassword(String password, String hashedPassword) {
		return encryptInf.isMatchPassword(password, hashedPassword);
	}
}
