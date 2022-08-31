package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.persistencia.entidad.Premio;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioPremio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ControladorPremio extends Controlador<Premio,RepositorioPremio> {
    @Override
    protected boolean validar(Premio obj) throws Exception {
        try {
            if (obj.getNombre().isEmpty()) {throw new Exception("Nombre no valido");}
            if (contieneSoloLetras(obj.getNombre()) == false) {throw new Exception("Nombre no valido, solo letras");}
            if (obj.getImagenLink().isEmpty()) {throw new Exception("Direccion no valida");}
            if (obj.getPrecio()<=0) {throw new Exception("Precio no valido");}
            if (obj.getProfesor()<=0) {throw new Exception("Profesor no valido");}
            if (obj.getStock()<0) {throw new Exception("Stock no valido");}
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
    public Premio guardar(Premio newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0) {
                    return getRepository().save(newer);
                }
                Premio original = getRepository().findById(newer.getId()).get();
                original.setNombre(newer.getNombre());
                original.setPrecio(newer.getPrecio());
                original.setStock(newer.getStock());
                original.setImagenLink(newer.getImagenLink());
                return getRepository().save(original);
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
}
