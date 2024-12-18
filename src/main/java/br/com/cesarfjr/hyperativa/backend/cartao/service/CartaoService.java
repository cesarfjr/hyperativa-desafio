package br.com.cesarfjr.hyperativa.backend.cartao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarfjr.hyperativa.backend.cartao.adapter.CartaoAdapter;
import br.com.cesarfjr.hyperativa.backend.cartao.dto.CartaoRequestDTO;
import br.com.cesarfjr.hyperativa.backend.cartao.dto.CartaoResponseDTO;
import br.com.cesarfjr.hyperativa.backend.cartao.entity.Cartao;
import br.com.cesarfjr.hyperativa.backend.cartao.repository.CartaoRepository;

@Service
public class CartaoService {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private CartaoAdapter cartaoAdapter;
	
	public CartaoResponseDTO salvaCartao(CartaoRequestDTO cartaoRequestDTO) {
		Cartao cartao = cartaoAdapter.adapt(cartaoRequestDTO);
		cartao = cartaoRepository.save(cartao);
		return new CartaoResponseDTO("Cartao inserido com sucesso", "00", cartao.getId());
	}
	
	
	public CartaoResponseDTO verificaCartao(String pan) {
		Cartao cartao = cartaoAdapter.adapt(pan);
		Optional<Cartao> optional = cartaoRepository.findByBinAndPan(cartao.getBin(), cartao.getPan());
		
		if(optional.isPresent()) {
			return new CartaoResponseDTO("Cartao existe na base", "00", optional.get().getId());
		}
		
		return new CartaoResponseDTO("Cartao nao existe na base", "99", cartao.getId());
	}
	
	
}
