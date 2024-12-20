package br.com.cesarfjr.hyperativa.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

	private String token;
	
}
