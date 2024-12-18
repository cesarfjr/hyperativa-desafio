package br.com.cesarfjr.hyperativa.backend.security.config;

import java.time.Duration;

import javax.crypto.SecretKey;

import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.OctetSequenceKey;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app.jwt")
public class AppJwtProperties {

	@NotNull
	private SecretKey key;

	@NotEmpty
	private String issuer;

	@NotNull
	private JWSAlgorithm algorithm;

	@NotNull
	@DurationMin(seconds = 1)
	private Duration expiresIn;

	public void setAlgorithm(String algorithm) {
		this.algorithm = JWSAlgorithm.parse(algorithm);
	}

	public void setKey(String key) {
		var jwk = new OctetSequenceKey.Builder(key.getBytes()).algorithm(algorithm).build();

		this.key = jwk.toSecretKey();
	}

}