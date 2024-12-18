package br.com.cesarfjr.hyperativa.backend.lote.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cesarfjr.hyperativa.backend.lote.model.Lote;
import br.com.cesarfjr.hyperativa.backend.lote.service.LoteFileParseService;

@RestController
@RequestMapping("/cartoes/lotes")
public class LoteController {

    @Autowired
    private LoteFileParseService loteFileParseService;

    @PreAuthorize("hasAuthority('SCOPE_GUEST')")
    @PostMapping
    public ResponseEntity<Object> cadastraLoteDeArquivo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty. Please upload a valid .txt file.");
        }

        try {
            // Process the uploaded file
            Lote loteFromFile = loteFileParseService.getLoteFromFile(file);
            return ResponseEntity.ok(loteFromFile);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }
}