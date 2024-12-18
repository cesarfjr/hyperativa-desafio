package br.com.cesarfjr.hyperativa.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.cesarfjr.hyperativa.backend.security.config.AppJwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppJwtProperties.class)
public class DesafioHyperativaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioHyperativaBackendApplication.class, args);
	}

}
