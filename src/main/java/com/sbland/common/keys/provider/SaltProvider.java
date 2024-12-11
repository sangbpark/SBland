package com.sbland.common.keys.provider;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltProvider {
	
    public String generateSalt(int saltLength) {
    	int saltByte = (int)Math.ceil((saltLength / (double)4)) * 3;
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[saltByte];
        secureRandom.nextBytes(salt); 
        return Base64.getEncoder().encodeToString(salt); 
    }
}
