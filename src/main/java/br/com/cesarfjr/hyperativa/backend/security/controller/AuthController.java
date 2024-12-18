package br.com.cesarfjr.hyperativa.backend.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarfjr.hyperativa.backend.security.service.BasicAuthService;
import br.com.cesarfjr.hyperativa.backend.security.service.JwtService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @Autowired
    private BasicAuthService basicAuthService;
    
    @PostMapping(path = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getToken(@RequestBody Map<String, Object> claims, @RequestHeader("Authorization") String authHeader) {
    	
    	if(basicAuthService.isValidUser(authHeader)) {
    		return new ResponseEntity<Object>(jwtService.generateJWT(claims), HttpStatus.OK);
    	}
		return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    }

}