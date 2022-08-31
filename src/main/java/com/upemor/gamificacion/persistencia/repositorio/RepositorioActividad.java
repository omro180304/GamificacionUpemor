
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Actividad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioActividad extends JpaRepository<Actividad,Long>{
    public List<Actividad> getByNombreLikeOrderByNombre(String nombre);
    public Actividad getByUnidad (long unidad);
    public Actividad getByNombre(String nombre);
    public List<Actividad> getByUnidadAndNombreLikeOrderByNombre(long unidad,String nombre);
    public Actividad getByNombreLikeAndObjUnidad_materia(String nombre, long materia);
} 
