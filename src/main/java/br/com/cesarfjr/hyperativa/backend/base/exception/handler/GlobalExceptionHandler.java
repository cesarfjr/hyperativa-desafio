package br.com.cesarfjr.hyperativa.backend.base.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.cesarfjr.hyperativa.backend.base.exception.dto.ExceptionResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> trataException(Exception e) {
		log.error("Ocorreu erro inesperado", e);
		ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Ocorreu erro inesperado: [" + e.getMessage() + "]", "EI");
		return new ResponseEntity<Object>(exceptionResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
}
