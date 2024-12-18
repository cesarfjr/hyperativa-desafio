package br.com.cesarfjr.hyperativa.backend.cartao.util;

public class MascaraUtil {

	public static String mascaraPan(String panParaMascarar) {
		String bin = panParaMascarar.substring(0,6);
		String ultimosDigitos = panParaMascarar.substring(panParaMascarar.length() - 4, panParaMascarar.length()); 
		int numeroAsteriscos = panParaMascarar.length() - 10;
		
		StringBuilder mascara = new StringBuilder();
		for(int i=0;i<numeroAsteriscos;i++) {
			mascara.append("*");
		}
		
		return bin + mascara.toString() + ultimosDigitos;
	}
	
}
