package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuario,Long>{
    public List<Usuario> getByNombresLikeOrderByNombres(String nombres);
    public List<Usuario> getByCorreoLikeOrderByCorreo(String correo);
    public Usuario getByCorreo(String correo);
} 
