
package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.upemor.gamificacion.persistencia.entidad.GrupoAlumno ;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioGrupoAlumno;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControladorGrupoAlumno extends Controlador<GrupoAlumno,RepositorioGrupoAlumno > {
    
    @Autowired private ControladorAlumno controladorAlumno;
    
    @Override
    protected boolean validar(GrupoAlumno  obj) throws Exception {
        try {
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public GrupoAlumno guardar(GrupoAlumno newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0) {
                    return getRepository().save(newer);
                }
                GrupoAlumno original = getRepository().findById(newer.getId()).get();
                original.setGrupo(newer.getGrupo());
                original.setAlumno(newer.getAlumno());
                return getRepository().save(original);
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
    
    public boolean importarDesdeCSV(Grupo grupo,String archivo, String separador) throws Exception {

        try {
            List<String> listData = Files.readAllLines(Paths.get(archivo));
            listData.remove(0);

            for (String data : listData) {
                String dts[] = data.split(separador);
                Alumno alumno = controladorAlumno.getRepository().getByMatricula(dts[0]);
                if(alumno != null){
                    GrupoAlumno grupoAlumno = getRepository().getByGrupoAndAlumno(grupo.getId(),alumno.getId());
                    if(grupoAlumno == null){
                        grupoAlumno = new GrupoAlumno();
                            grupoAlumno.setGrupo(grupo.getId());
                            grupoAlumno.setAlumno(alumno.getId());
                       guardar(grupoAlumno);
                    }
                }
            }
            return true;
        } catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
