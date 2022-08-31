
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.ClasificacionTipo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioClasificacionTipo extends JpaRepository<ClasificacionTipo,Long>{
    public List<ClasificacionTipo> getByNombreLikeOrderByNombre(String nombre);
} 
