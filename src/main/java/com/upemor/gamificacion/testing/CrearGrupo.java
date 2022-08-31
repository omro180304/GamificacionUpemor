package com.upemor.gamificacion.testing;

import com.upemor.gamificacion.controladores.ControladorGrupo;
import com.upemor.gamificacion.persistencia.entidad.Grupo;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrearGrupo{
    @Autowired private ControladorGrupo controlador;
    
    public boolean crearDatos()throws Exception{
        try{
            if(!controlador.getRepository().findAll().isEmpty()) return false;
            Grupo obj = new Grupo();
                obj.setGeneracion("2018");
                obj.setNombre("8B");
                obj.setTurno(2);
                controlador.guardar(obj);
            obj = new Grupo();
                obj.setGeneracion("2018");
                obj.setNombre("8A");
                obj.setTurno(1);
                controlador.guardar(obj);
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
