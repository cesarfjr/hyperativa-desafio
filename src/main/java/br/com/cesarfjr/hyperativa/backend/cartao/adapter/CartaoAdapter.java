package br.com.cesarfjr.hyperativa.backend.cartao.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cesarfjr.hyperativa.backend.base.encryption.AesEncryptionUtil;
import br.com.cesarfjr.hyperativa.backend.cartao.dto.CartaoRequestDTO;
import br.com.cesarfjr.hyperativa.backend.cartao.entity.Cartao;

@Component
public class CartaoAdapter {

	@Autowired
	private AesEncryptionUtil aesEncryptionUtil;
	
	public Cartao adapt(CartaoRequestDTO cartaoRequestDTO) {
		Cartao cartao = new Cartao();
		cartao.setBin(obtemBinCartao(cartaoRequestDTO.getPan()));
		try {
			cartao.setPan(aesEncryptionUtil.encrypt(cartaoRequestDTO.getPan()));
			cartao.setId(cartao.getPan());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartao;
		
	}
	
	private String obtemBinCartao(String pan) {
		return pan.substring(0, 6);
	}

	public Cartao adapt(String pan) {
		Cartao cartao = new Cartao();
		cartao.setBin(obtemBinCartao(pan));
		try {
			cartao.setPan(aesEncryptionUtil.encrypt(pan));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartao;
	}
	
	
}
