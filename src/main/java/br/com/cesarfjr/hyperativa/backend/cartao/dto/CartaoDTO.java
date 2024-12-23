package br.com.cesarfjr.hyperativa.backend.cartao.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDTO implements Serializable {
	
	private static final long serialVersionUID = 6254226959353288587L;
	
	private String idCartao;
	private String pan;

}
