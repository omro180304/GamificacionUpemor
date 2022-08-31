
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Materia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMateria extends JpaRepository<Materia,Long>{
     public List<Materia> getByNombreLikeOrderByNombre(String string);
     public Materia getByNombre(String nombre);
     

} 
