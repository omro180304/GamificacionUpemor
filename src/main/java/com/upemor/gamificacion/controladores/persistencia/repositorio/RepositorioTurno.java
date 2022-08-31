
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Archivos Propios
 */
public interface RepositorioTurno extends JpaRepository<Turno,Long>{
    public List<Turno> getByNombreLikeOrderByNombre(String nombre);
} 
