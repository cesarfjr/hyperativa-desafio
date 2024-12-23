package br.com.cesarfjr.hyperativa.backend.cartao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarfjr.hyperativa.backend.cartao.dto.CartaoRequestDTO;
import br.com.cesarfjr.hyperativa.backend.cartao.service.CartaoService;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;
	
	@PreAuthorize("hasAuthority('SCOPE_GUEST')")
	@PostMapping
	public ResponseEntity<Object> cadastraCartao(@RequestBody CartaoRequestDTO cartaoRequestDTO) throws Exception {
		return new ResponseEntity<Object>(cartaoService.salvaCartao(cartaoRequestDTO), HttpStatus.OK) ;
	}
	
	@PreAuthorize("hasAuthority('SCOPE_GUEST')")
	@GetMapping("/{pan}")
	public ResponseEntity<Object> verificaCartao(@PathVariable("pan") String pan) throws Exception {
		return new ResponseEntity<Object>(cartaoService.verificaCartao(pan), HttpStatus.OK);
	}
	
}
