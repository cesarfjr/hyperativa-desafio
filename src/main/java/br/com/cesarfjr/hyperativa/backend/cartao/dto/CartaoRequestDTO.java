package br.com.cesarfjr.hyperativa.backend.cartao.dto;

import java.io.Serializable;

import br.com.cesarfjr.hyperativa.backend.cartao.util.MascaraUtil;
import lombok.Data;

@Data
public class CartaoRequestDTO implements Serializable {
	
	private static final long serialVersionUID = -3678491876996908322L;
	
	private String pan;
	
	@Override
	public String toString() {
		return "CartaoRequestDTO(pan=" + MascaraUtil.mascaraPan(pan) + ")";
	}
}
