package com.upemor.gamificacion.testing;

import com.upemor.gamificacion.controladores.ControladorMateria;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrearMateria{
    @Autowired private ControladorMateria controlador;
    
    public boolean crearDatos()throws Exception{
        try{
            if(!controlador.getRepository().findAll().isEmpty()) return false;
            Materia obj = new Materia();
                obj.setNombre("Programacion avanzada");
                controlador.guardar(obj);
            obj = new Materia();
                obj.setNombre("Expresion oral y escrita");
                controlador.guardar(obj);
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
