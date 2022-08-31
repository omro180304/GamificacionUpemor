
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Unidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/*@author Archivos Propios
 */
public interface RepositorioUnidad extends JpaRepository<Unidad,Long>{
    public List<Unidad> getByNombreLikeOrderByNombre(String nombre);
    public Unidad getByNombre(String nombre);
} 
