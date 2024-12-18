package br.com.cesarfjr.hyperativa.backend.security.service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.com.cesarfjr.hyperativa.backend.security.config.AppJwtProperties;

@Component
public class JwtService {

	// baseado na implementacao em:
	// https://medium.com/@HereAndBeyond/spring-boot-3-jwt-authorization-based-on-a-secret-key-ababbb3a3350
	
	@Autowired
    private AppJwtProperties appJwtProperties;


    public String generateJWT(String basicAuthHeader) {
    	
    	Map<String, Object> claims = generateClaims(basicAuthHeader);
    	
    	
        var key = appJwtProperties.getKey();
        var algorithm = appJwtProperties.getAlgorithm();

        var header = new JWSHeader(algorithm);
        var claimsSet = buildClaimsSet(claims);

        var jwt = new SignedJWT(header, claimsSet);

        try {
            var signer = new MACSigner(key);
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException("Unable to generate JWT", e);
        }

        return jwt.serialize();
    }

    private Map<String, Object> generateClaims(String basicAuthHeader) {
		String base64Credentials = basicAuthHeader.substring("Basic ".length());
		String credentials = new String(Base64.getDecoder().decode(base64Credentials));
		String[] values = credentials.split(":", 2);
		String username = values[0];
//		String password = values[1];
    	
		Map<String, Object> claims = new HashMap<>();
		String[] scopeRoles = new String[] {"role1", "role2", "GUEST"};
		
		claims.put("sub", username);
		claims.put("scope", scopeRoles);
		
		return claims;
	}

	private JWTClaimsSet buildClaimsSet(Map<String, Object> claims) {
        var issuer = appJwtProperties.getIssuer();
        var issuedAt = Instant.now();
        var expirationTime = issuedAt.plus(appJwtProperties.getExpiresIn());

        var builder = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .issueTime(Date.from(issuedAt))
                .expirationTime(Date.from(expirationTime));

        claims.forEach(builder::claim);

        return builder.build();
    }

}
