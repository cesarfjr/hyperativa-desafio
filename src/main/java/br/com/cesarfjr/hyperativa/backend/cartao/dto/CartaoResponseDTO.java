package br.com.cesarfjr.hyperativa.backend.cartao.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CartaoResponseDTO implements Serializable {

	private static final long serialVersionUID = -3708712079022765193L;
	
	private String msgResp;
	private String codResp;
	private List<CartaoDTO> cartoes;
	
	public CartaoResponseDTO(String msgResp, String codResp, List<CartaoDTO> cartoes) {
		this.msgResp = msgResp;
		this.codResp = codResp;
		this.cartoes = cartoes;
	}
	
	public CartaoResponseDTO(String msgResp, String codResp, CartaoDTO cartao) {
		this.msgResp = msgResp;
		this.codResp = codResp;
		this.cartoes = new ArrayList<>();
		this.cartoes.add(cartao);
	}
	
}
