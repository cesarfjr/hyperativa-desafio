package br.com.cesarfjr.hyperativa.backend.lote.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CabecalhoLote implements Serializable {
	
	private static final long serialVersionUID = 1910060103545108870L;
	
	private String nome;
	private String data;
	private String identificadorLote;
	private Integer quantidadeRegistros;
	
}
