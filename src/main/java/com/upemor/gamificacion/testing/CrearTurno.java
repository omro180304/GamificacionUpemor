package com.upemor.gamificacion.testing;

import com.upemor.gamificacion.controladores.ControladorTurno;
import com.upemor.gamificacion.persistencia.entidad.Turno;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrearTurno{
    @Autowired private ControladorTurno controlador;
    
    public boolean crearDatos()throws Exception{
        try{
            if(!controlador.getRepository().findAll().isEmpty()) return false;
            Turno obj = new Turno();
                obj.setNombre("Matutino");
                controlador.guardar(obj);
            obj = new Turno();
                obj.setNombre("Vespertino");
                controlador.guardar(obj);
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
