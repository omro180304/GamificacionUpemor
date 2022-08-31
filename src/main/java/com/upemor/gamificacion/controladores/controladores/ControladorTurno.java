
package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.persistencia.entidad.Turno;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioTurno;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ControladorTurno extends Controlador<Turno,RepositorioTurno> {
    @Override
    protected boolean validar(Turno obj) throws Exception {
        try {
            if (obj.getNombre().isEmpty()) {throw new Exception("Nombre no valido");}
            if (contieneSoloLetras(obj.getNombre())== false){throw new Exception("Nombre no valido, solo letras");}
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
    
    public static boolean contieneSoloLetras(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ' || c == 'ñ')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Turno guardar(Turno newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0) {
                    return getRepository().save(newer);
                }
                Turno original = getRepository().findById(newer.getId()).get();
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
