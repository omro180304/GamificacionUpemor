
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Alumno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioAlumno extends JpaRepository<Alumno,Long>{
    public List<Alumno> getByMatriculaLikeOrderByMatricula(String matricula);
    public List<Alumno> getByNombresLikeOrderByNombres(String nombres);
    public Alumno getByMatricula(String matricula);
    public Alumno getByNombres(String nombres);
    public Alumno getByCorreo(String correo);

}
