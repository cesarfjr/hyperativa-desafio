package br.com.cesarfjr.hyperativa.backend.cartao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cartao {

	@Id
	private String id;
	private String pan;
	private String bin;
	
}
