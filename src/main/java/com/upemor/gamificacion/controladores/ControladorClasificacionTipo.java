
package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.persistencia.entidad.ClasificacionTipo;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioClasificacionTipo;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ControladorClasificacionTipo extends Controlador<ClasificacionTipo,RepositorioClasificacionTipo> {
    @Override
    protected boolean validar(ClasificacionTipo obj) throws Exception {
        try {
            if (obj.getNombre().isEmpty()) {throw new Exception("Nombre no valido");}
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public ClasificacionTipo guardar(ClasificacionTipo newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0) {
                    return getRepository().save(newer);
                }
                ClasificacionTipo original = getRepository().findById(newer.getId()).get();
                original.setNombre(newer.getNombre());
                return getRepository().save(original);
            }
        } catch (Exception ex) {
           Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
}
