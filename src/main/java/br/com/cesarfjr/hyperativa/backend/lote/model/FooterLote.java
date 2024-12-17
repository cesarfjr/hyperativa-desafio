package br.com.cesarfjr.hyperativa.backend.lote.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class FooterLote implements Serializable {

	private static final long serialVersionUID = 5959684118193213783L;

	private String identificadorLote;
	private Integer quantidadeRegistros;
	
}
