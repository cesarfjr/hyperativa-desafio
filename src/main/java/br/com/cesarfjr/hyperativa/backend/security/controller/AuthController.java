package br.com.cesarfjr.hyperativa.backend.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarfjr.hyperativa.backend.security.dto.AuthResponseDTO;
import br.com.cesarfjr.hyperativa.backend.security.service.BasicAuthService;
import br.com.cesarfjr.hyperativa.backend.security.service.JwtService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private JwtService jwtService;

    @Autowired
    private BasicAuthService basicAuthService;
    
    @GetMapping(path = "/token")
    public ResponseEntity<Object> getToken(@RequestHeader(name = "Authorization", required = false) String authHeader) {
    	
    	if(basicAuthService.isValidUser(authHeader)) {
    		return new ResponseEntity<Object>(new AuthResponseDTO(jwtService.generateJWT(authHeader)), HttpStatus.OK);
    	}
		return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    }

}