package com.upemor.gamificacion.persistencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.upemor.gamificacion.persistencia.entidad.Premio;
import java.util.List;

public interface RepositorioPremio extends JpaRepository<Premio,Long>{
    public List<Premio> getByNombreLikeOrderByNombre(String nombre);
    

} 
