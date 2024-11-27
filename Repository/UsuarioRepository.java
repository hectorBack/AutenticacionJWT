package com.example.BorrarLoginJWT.Repository;

import com.example.BorrarLoginJWT.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreAndContrasenia(String nombre, String contrasenia);
    Usuario findByNombre(String nombre);
}
