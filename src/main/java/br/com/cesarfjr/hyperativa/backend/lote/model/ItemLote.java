package br.com.cesarfjr.hyperativa.backend.lote.model;

import java.io.Serializable;

import br.com.cesarfjr.hyperativa.backend.cartao.util.MascaraUtil;
import lombok.Data;

@Data
public class ItemLote implements Serializable {
	
	private static final long serialVersionUID = 6772194354748962069L;

	private String identificadorLinha;
	private String numeroRegistro;
	private String pan;
	
	
	@Override
	public String toString() {
		return "ItemLote(identificadorLinha=" + identificadorLinha + ", numeroRegistro=" + numeroRegistro + ", pan=" + MascaraUtil.mascaraPan(pan) + ")";
	}



}
