package br.com.cesarfjr.hyperativa.backend.lote.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.cesarfjr.hyperativa.backend.cartao.adapter.CartaoAdapter;
import br.com.cesarfjr.hyperativa.backend.cartao.dto.CartaoDTO;
import br.com.cesarfjr.hyperativa.backend.cartao.entity.Cartao;
import br.com.cesarfjr.hyperativa.backend.cartao.service.CartaoService;
import br.com.cesarfjr.hyperativa.backend.lote.dto.LoteDTO;
import br.com.cesarfjr.hyperativa.backend.lote.dto.LoteResponseDTO;
import br.com.cesarfjr.hyperativa.backend.lote.model.ItemLote;
import br.com.cesarfjr.hyperativa.backend.lote.model.Lote;

@Service
public class LoteService {

	@Autowired
	private LoteFileParseService loteFileParseService;
	@Autowired
	private CartaoAdapter cartaoAdapter;
	@Autowired
	private CartaoService cartaoService;
	
	public LoteResponseDTO cadastraLoteDeArquivo(MultipartFile file) throws Exception {
		
		Lote loteFromFile = loteFileParseService.getLoteFromFile(file);

		LoteDTO loteDTO = new LoteDTO();
		loteDTO.setData(loteFromFile.getCabecalhoLote().getData());
		loteDTO.setIdentificadorLote(loteFromFile.getCabecalhoLote().getIdentificadorLote());
		loteDTO.setNome(loteFromFile.getCabecalhoLote().getNome());
		loteDTO.setQuantidadeRegistros(loteFromFile.getCabecalhoLote().getQuantidadeRegistros());
		
		LoteResponseDTO loteResponseDTO = new LoteResponseDTO("Lote inserido com sucesso", "00", new ArrayList<CartaoDTO>(), loteDTO);
		
		for (ItemLote itemLote : loteFromFile.getListaItensLote()) {
			Cartao cartao = cartaoAdapter.adapt(itemLote.getPan());
			cartao = cartaoService.salvaCartao(cartao);
			loteResponseDTO.getCartoes().add(new CartaoDTO(cartao.getId(), null));
		}
		
		return loteResponseDTO;
	}

}
