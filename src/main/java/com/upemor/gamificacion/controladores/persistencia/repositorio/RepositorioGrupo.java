
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Grupo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioGrupo extends JpaRepository<Grupo,Long>{
    public List<Grupo> getByNombreLikeOrderByNombre(String nombre);
    //public Materia getByNombre(String nombre);
    public Grupo getByNombre(String nombre);

} 
