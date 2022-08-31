
package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioGrupo;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ControladorGrupo extends Controlador<Grupo,RepositorioGrupo> {
    @Override
    protected boolean validar(Grupo obj) throws Exception {
        try {
            if (obj.getNombre().isEmpty()) {throw new Exception("Nombre no valido");}
            if (obj.getGeneracion().isEmpty()) {throw new Exception("Generación no valida");}
            if (obj.getTurno() == 0) {throw new Exception("Turno no valido");}
            //Grupo grupo = getRepository().getByNombre(obj.getNombre());
            //if (obj.getNombre().equals(grupo.getNombre())) {throw new Exception("Grupo ya registrado");}
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Grupo guardar(Grupo newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0) {
                    return getRepository().save(newer);
                }
                Grupo original = getRepository().findById(newer.getId()).get();
                original.setNombre(newer.getNombre());
                original.setTurno(newer.getTurno());
                original.setGeneracion(newer.getGeneracion());
                return getRepository().save(original);
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
}
