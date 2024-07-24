package com.app.contador.services;

import java.util.List;
import java.util.Set;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = getById(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(username)
                .password(usuario.password())
                .roles(usuario.roles().toArray(new String[0]))
                .build();
    }

    public record Usuario(String username, String password, Set<String> roles) {};

    public static Usuario getById(String username) {
        // "secreto" => [BCrypt] => "$2a$10$56VCAiApLO8NQYeOPiu2De/EBC5RWrTZvLl7uoeC3r7iXinRR1iiq"
        var password = "$2a$10$56VCAiApLO8NQYeOPiu2De/EBC5RWrTZvLl7uoeC3r7iXinRR1iiq";
        Usuario juan = new Usuario(
                "jcabelloc",
                password,
                Set.of("USER")
        );

        Usuario maria = new Usuario(
                "mlopez",
                password,
                Set.of("ADMIN")
        );
        var usuarios = List.of(juan, maria);

        return usuarios
                .stream()
                .filter(e -> e.username().equals(username))
                .findFirst()
                .orElse(null);
    }
}























//package com.app.contador.services;
//import com.app.contador.controller.CarroControlador;
//import com.app.contador.modelo.UserLogin;
//import com.app.contador.repositorio.UsuariosRepositorio;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    UsuariosRepositorio usuariosRepositorio;
//
//    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class.getName());
////    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
////        UserLogin usuario =  usuariosRepositorio.getUsuarioByUsu(username);
//
//
//        UserLogin usuario = new UserLogin();
//        var pass = "$2a$10$xLJ9TU1sfY0KFJmsHqeIteEuMjKNonNf2a6gNBznHgua590FcbuNS";
//
//        usuario.setUsu("roberto");
//        usuario.setPass(pass);
//        usuario.setRol("admin");
//
//
//        if(usuario == null) {
//            throw new UsernameNotFoundException(username);
//        }
//
//        return User
//                .withUsername(username)
//                .password(usuario.getPass())
//                .roles("admin")
//                .build();
//    }
//}
