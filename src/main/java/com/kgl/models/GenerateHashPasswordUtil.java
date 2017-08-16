package com.kgl.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class GenerateHashPasswordUtil {

	public static String generateHash(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}


}
