
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.UsuarioTipo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/*@author Archivos Propios*/

public interface RepositorioUsuarioTipo extends JpaRepository<UsuarioTipo,Long>{
    public List<UsuarioTipo> getByNombreLikeOrderByNombre(String nombre);
} 
