
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.GrupoMateriaProfesor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioGrupoMateriaProfesor extends JpaRepository<GrupoMateriaProfesor,Long>{
    public List<GrupoMateriaProfesor> getByGrupoAndObjMateria_nombreLikeOrderByObjMateria_nombre(long grupo,String nombre);  
} 
