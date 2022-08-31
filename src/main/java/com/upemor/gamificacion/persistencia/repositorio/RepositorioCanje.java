
package com.upemor.gamificacion.persistencia.repositorio;

import com.upemor.gamificacion.persistencia.entidad.Canje;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCanje extends JpaRepository<Canje,Long>{
       public List<Canje> getByObjAlumno_nombresLike(String nombres);
       public List<Canje> getByAlumnoInAndPremioInAndFechaBetween(List<Long> alumnos,List<Long> grupo,LocalDateTime fechaInicio,LocalDateTime fechaFinal);


} 
