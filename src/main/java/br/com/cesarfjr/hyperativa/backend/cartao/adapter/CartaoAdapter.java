package br.com.cesarfjr.hyperativa.backend.cartao.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cesarfjr.hyperativa.backend.base.encryption.AesEncryptionUtil;
import br.com.cesarfjr.hyperativa.backend.cartao.dto.CartaoRequestDTO;
import br.com.cesarfjr.hyperativa.backend.cartao.entity.Cartao;
import br.com.cesarfjr.hyperativa.backend.cartao.util.IdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CartaoAdapter {

	@Autowired
	private AesEncryptionUtil aesEncryptionUtil;
	
	public Cartao adapt(CartaoRequestDTO cartaoRequestDTO) throws Exception {
		Cartao cartao = new Cartao();
		cartao.setBin(obtemBinCartao(cartaoRequestDTO.getPan()));
		cartao.setId(IdGeneratorUtil.getNewId());
		try {
			cartao.setPan(aesEncryptionUtil.encrypt(cartaoRequestDTO.getPan()));
		} catch (Exception e) {
			log.error("Nao foi possivel criptografar pan: [{}]", e.getMessage());
			throw e;
		}
		return cartao;
		
	}
	
	private String obtemBinCartao(String pan) {
		return pan.substring(0, 6);
	}

	public Cartao adapt(String pan) throws Exception {
		Cartao cartao = new Cartao();
		cartao.setBin(obtemBinCartao(pan));
		cartao.setId(IdGeneratorUtil.getNewId());
		try {
			cartao.setPan(aesEncryptionUtil.encrypt(pan));
		} catch (Exception e) {
			log.error("Nao foi possivel criptografar pan: [{}]", e.getMessage());
			throw e;
		}
		return cartao;
	}
	
	
}
