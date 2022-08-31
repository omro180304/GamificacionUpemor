
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Clasificacion;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioClasificacion extends JpaRepository<Clasificacion,Long>{
    public List<Clasificacion> getByObjAlumno_nombresLike(String nombres);   
    public Clasificacion getByAlumnoAndActividad(long alumno,long actividad);
    public List<Clasificacion> getByAlumnoInAndActividadInAndFechaBetween(List<Long> alumnos,List<Long> actividades,LocalDateTime fechaInicio,LocalDateTime fechaFinal);

} 
