package br.com.cesarfjr.hyperativa.backend.cartao.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.cesarfjr.hyperativa.backend.cartao.entity.Cartao;

public interface CartaoRepository extends CrudRepository<Cartao, String> {

	public Optional<Cartao> findByBinAndPan(String bin, String pan);

}
