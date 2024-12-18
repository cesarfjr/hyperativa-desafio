package br.com.cesarfjr.hyperativa.backend.lote.dto;

import java.util.List;

import br.com.cesarfjr.hyperativa.backend.cartao.dto.CartaoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoteResponseDTO {

	private String msgResp;
	private String codResp;
	private List<CartaoDTO> cartoes;
	private LoteDTO lote;
	
}
