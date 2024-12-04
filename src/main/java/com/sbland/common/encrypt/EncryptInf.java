package com.sbland.common.encrypt;

public interface EncryptInf {
	public String hashedPassword(String password);
	public boolean isMatchPassword(String password, String hashedPassword);
}
