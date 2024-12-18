package br.com.cesarfjr.hyperativa.backend.security.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class BasicAuthService {

	private static final String EXPECTED_USERNAME = "usuario";
	private static final String EXPECTED_PASSWORD = "senha";

	public boolean isValidUser(String authorizationHeader) {

		if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
			String base64Credentials = authorizationHeader.substring("Basic ".length());
			String credentials = new String(Base64.getDecoder().decode(base64Credentials));
			String[] values = credentials.split(":", 2);
			String username = values[0];
			String password = values[1];

			return EXPECTED_USERNAME.equals(username) && EXPECTED_PASSWORD.equals(password);
		}

		return false;

	}

}
