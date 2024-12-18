package br.com.cesarfjr.hyperativa.backend.base.exception.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponseDTO implements Serializable {

	private static final long serialVersionUID = -5717189441272827946L;

	private String msgResp;
	private String codResp;
}
