
package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.persistencia.entidad.GrupoMateriaProfesor;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioGrupoMateriaProfesor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ControladorGrupoMateriaProfesor extends Controlador<GrupoMateriaProfesor,RepositorioGrupoMateriaProfesor> {
    @Override
    protected boolean validar(GrupoMateriaProfesor obj) throws Exception {
        try {
            if (obj.getMateria()<=0) {throw new Exception("Materia no valido");}
            if (obj.getProfesor()<=0) {throw new Exception("Profesor no valido");}

            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public GrupoMateriaProfesor guardar(GrupoMateriaProfesor newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0) {
                    return getRepository().save(newer);
                }
                GrupoMateriaProfesor original = getRepository().findById(newer.getId()).get();
                original.setMateria(newer.getMateria());
                original.setProfesor(newer.getProfesor());
                return getRepository().save(original);
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
}
