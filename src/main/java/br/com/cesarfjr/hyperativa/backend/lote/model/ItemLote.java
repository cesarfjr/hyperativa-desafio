package br.com.cesarfjr.hyperativa.backend.lote.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemLote implements Serializable {
	
	private static final long serialVersionUID = 6772194354748962069L;

	private String identificadorLinha;
	private String numeroRegistro;
	private String pan;
	
	
	@Override
	public String toString() {
		return "ItemLote(identificadorLinha=" + identificadorLinha + ", numeroRegistro=" + numeroRegistro + ", pan=" + mascaraPan(pan) + ")";
	}


	private String mascaraPan(String panParaMascarar) {
		String bin = panParaMascarar.substring(0,6);
		String ultimosDigitos = panParaMascarar.substring(panParaMascarar.length() - 4, panParaMascarar.length()); 
		return bin + "*********" + ultimosDigitos;
	}
}
