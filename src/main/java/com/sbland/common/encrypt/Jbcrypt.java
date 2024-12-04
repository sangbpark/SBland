package com.sbland.common.encrypt;

import org.mindrot.jbcrypt.BCrypt;

public class Jbcrypt implements EncryptInf {

	@Override
	public String hashedPassword(String password) { 
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public boolean isMatchPassword(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}

}
