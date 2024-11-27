package com.example.BorrarLoginJWT.Controller;

import com.example.BorrarLoginJWT.Entity.Usuario;
import com.example.BorrarLoginJWT.Repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsuarioController {

    private String secretKey = "mySecretKeyItsteziutlanWebServices";

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        Usuario user = usuarioRepository.findByNombreAndContrasenia(username, password);
        if (user != null) {
            String token = getJWTToken(username);
            // Usuario user = new Usuario();
            user.setNombre(username);
            user.setToken(token);
            return new ResponseEntity<Usuario>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario newUser) {
        // Verificar si el usuario ya existe
        if (usuarioRepository.findByNombre(newUser.getNombre()) != null) {
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
        }

        // Validar que los datos sean correctos (puedes agregar más validaciones)
        if (newUser.getNombre() == null || newUser.getContrasenia() == null) {
            return new ResponseEntity<>("Nombre y contraseña son requeridos", HttpStatus.BAD_REQUEST);
        }

        // Guardar el nuevo usuario
        Usuario savedUser = usuarioRepository.save(newUser);

        // Retornar el usuario creado (puedes omitir la contraseña al retornar la respuesta)
        savedUser.setContrasenia(null);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    public String getJWTToken(String username) {
        // String secretKey = "mySecretKeyItsteziutlanWebServices";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts.builder().setId("itstJWT").setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000)).signWith(getSigningKey()).compact();

        return "Bearer " + token;
    }

    private Key getSigningKey() {
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
