
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.GrupoAlumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioGrupoAlumno extends JpaRepository<GrupoAlumno,Long>{
    public GrupoAlumno getByGrupoAndAlumno(long grupo,long alumno);
} 
