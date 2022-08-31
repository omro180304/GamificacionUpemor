
package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.persistencia.entidad.Actividad;
import com.upemor.gamificacion.persistencia.entidad.Unidad;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioActividad;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControladorActividad extends Controlador<Actividad,RepositorioActividad> {
    @Autowired private ControladorUnidad controladorUnidad;
    
    //Metodo que hace la validacion de datos antes de ser almacenados
    @Override protected boolean validar(Actividad obj) throws Exception {
        try {
            if (obj.getNombre().isEmpty()) {throw new Exception("Nombre no valido");}
            if (obj.getPuntaje()<=0) {throw new Exception("Puntaje no valido");}
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
    
    @Override public Actividad guardar(Actividad newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0) {
                    return getRepository().save(newer);
                }
                Actividad original = getRepository().findById(newer.getId()).get();
                original.setNombre(newer.getNombre());
                original.setPuntaje(newer.getPuntaje());
                return getRepository().save(original);
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
       
    public boolean importarDesdeCSV(Unidad unidad, String archivo, String separador) throws Exception {
        try {
            List<String> listData = Files.readAllLines(Paths.get(archivo));
            listData.remove(0);
            for (String data : listData) {
                String dts[] = data.split(separador);
               
                Actividad actividad = getRepository().getByNombre(dts[0]);
                if (actividad == null) {
                    actividad = new Actividad();
                    actividad.setUnidad(unidad.getId());
                }
                actividad.setPuntaje(Long.parseLong(dts[1]));
                actividad.setNombre(dts[0]);
                
                getRepository().save(actividad);
            }
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}

