package com.bus.app.controller;

import com.bus.app.modelo.AuthenticationReq;
import com.bus.app.modelo.TokenInfo;
import com.bus.app.services.JwtUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService usuarioDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq) {
        logger.info("Autenticando al usuario {}", authenticationReq.getUsuario());

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),
                        authenticationReq.getClave()));

        final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(authenticationReq.getUsuario());
        final String jwt = jwtUtilService.generateToken(userDetails);
        return ResponseEntity.ok(new TokenInfo(jwt));
    }

    @GetMapping("/authenticationTest")
    public ResponseEntity<String> prueba() {
        return ResponseEntity.ok("Estas autenticado correctamente");
    }
}