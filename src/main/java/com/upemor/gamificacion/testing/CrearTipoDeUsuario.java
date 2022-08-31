package com.upemor.gamificacion.testing;

import com.upemor.gamificacion.controladores.ControladorUsuarioTipo;
import com.upemor.gamificacion.persistencia.entidad.UsuarioTipo;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrearTipoDeUsuario{
    @Autowired private ControladorUsuarioTipo controlador;
    
    public boolean crearDatos()throws Exception{
        try{
            if(!controlador.getRepository().findAll().isEmpty()) return false;
            UsuarioTipo obj = new UsuarioTipo();
                obj.setNombre("Administrador");
                controlador.guardar(obj);
            obj = new UsuarioTipo();
                obj.setNombre("Profesor");
                controlador.guardar(obj);
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
