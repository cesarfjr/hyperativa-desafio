package br.com.cesarfjr.hyperativa.backend.lote.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.cesarfjr.hyperativa.backend.lote.model.ItemLote;
import br.com.cesarfjr.hyperativa.backend.lote.model.Lote;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoteFileParseService {

    public Lote getLoteFromFile(MultipartFile multipartFile) throws IOException {
        // Convert MultipartFile to a File
        File file = convertMultipartFileToFile(multipartFile);

        Lote lote = new Lote();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String cabecalho = br.readLine(); // primeira linha sempre cabecalho
            lote.getCabecalhoLote().setNome(cabecalho.substring(0, 29));
            lote.getCabecalhoLote().setData(cabecalho.substring(29, 37));
            lote.getCabecalhoLote().setIdentificadorLote(cabecalho.substring(37, 45));
            lote.getCabecalhoLote().setQuantidadeRegistros(Integer.parseInt(cabecalho.substring(45, 51)));
                        
            String registro;
            int numeroRegistro = 1;
            while (((registro = br.readLine()) != null)) {
            	ItemLote itemLote = new ItemLote();
            	itemLote.setIdentificadorLinha(registro.substring(0, 1));
            	itemLote.setNumeroRegistro(registro.substring(1, 7).trim());
            	itemLote.setPan(registro.substring(7, 51).trim());
            	lote.getListaItensLote().add(itemLote);
                numeroRegistro++;
                if(numeroRegistro > lote.getCabecalhoLote().getQuantidadeRegistros()) {
                	break;
                }
                
            }
            
            String footer = br.readLine(); // ultima linha sempre footer
            lote.getFooterLote().setIdentificadorLote(footer.substring(0, 8));
            lote.getFooterLote().setQuantidadeRegistros(Integer.parseInt(footer.substring(8, 14)));
            
            if(lote.getListaItensLote().size() != lote.getFooterLote().getQuantidadeRegistros()) {
            	log.warn("Quantidade de Registros indicado no footer [{}] diferente do numero de registros indicados no cabecalho [{}]", lote.getFooterLote().getQuantidadeRegistros(), lote.getCabecalhoLote().getQuantidadeRegistros());
            }
            
            log.info("[{}] itens do lote parseados com sucesso:\n [{}]", lote.getListaItensLote().size(), lote);
        }

        return lote;
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Create a temporary file
        File tempFile = File.createTempFile("uploaded-", ".txt");

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }

        return tempFile;
    }
}
