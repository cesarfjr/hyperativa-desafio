package br.com.cesarfjr.hyperativa.backend.cartao.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartaoResponseDTO implements Serializable {

	private static final long serialVersionUID = -3708712079022765193L;
	
	private String msgResp;
	private String codResp;
	private String idCartao;
	
}
