package br.com.cesarfjr.hyperativa.backend.cartao.util;

import java.util.UUID;

public class IdGeneratorUtil {

	public static String getNewId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}
