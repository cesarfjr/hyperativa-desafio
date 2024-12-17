package br.com.cesarfjr.hyperativa.backend.lote.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Lote implements Serializable {
	
	private static final long serialVersionUID = -8443514454725313413L;
	
	private CabecalhoLote cabecalhoLote = new CabecalhoLote();
	private List<ItemLote> listaItensLote = new ArrayList<ItemLote>();
	private FooterLote footerLote = new FooterLote();

}
