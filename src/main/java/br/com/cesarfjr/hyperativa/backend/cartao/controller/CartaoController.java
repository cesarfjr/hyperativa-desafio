package br.com.cesarfjr.hyperativa.backend.cartao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarfjr.hyperativa.backend.cartao.dto.CartaoRequestDTO;

@RestController
@RequestMapping("")
public class CartaoController {

	@PostMapping
	public ResponseEntity<Object> cadastraCartao(@RequestBody CartaoRequestDTO cartaoRequestDTO) {
		return null;
	}
	
	@GetMapping
	public ResponseEntity<Object> verificaCartao(@RequestParam("pan") String pan) {
		return null;
	}
	
}
