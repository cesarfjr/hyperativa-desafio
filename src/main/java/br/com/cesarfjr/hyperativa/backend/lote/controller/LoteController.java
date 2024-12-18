package br.com.cesarfjr.hyperativa.backend.lote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cesarfjr.hyperativa.backend.lote.service.LoteService;

@RestController
@RequestMapping("/cartoes/lotes")
public class LoteController {

	@Autowired
	private LoteService loteService;

	@PreAuthorize("hasAuthority('SCOPE_GUEST')")
	@PostMapping
	public ResponseEntity<Object> cadastraLoteDeArquivo(@RequestParam("arquivo") MultipartFile file) throws Exception {
		return ResponseEntity.ok(loteService.cadastraLoteDeArquivo(file));
	}
}