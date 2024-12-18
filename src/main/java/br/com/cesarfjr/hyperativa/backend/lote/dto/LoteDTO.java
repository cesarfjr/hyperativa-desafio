package br.com.cesarfjr.hyperativa.backend.lote.dto;

import lombok.Data;

@Data
public class LoteDTO {

	private String nome;
	private String data;
	private String identificadorLote;
	private Integer quantidadeRegistros;
	
}
