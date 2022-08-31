package com.upemor.gamificacion.testing;

import com.upemor.gamificacion.controladores.ControladorClasificacionTipo;
import com.upemor.gamificacion.persistencia.entidad.ClasificacionTipo;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrearTipoDeClasificacion{
    @Autowired private ControladorClasificacionTipo controlador;
    
    public boolean crearDatos()throws Exception{
        try{
            if(!controlador.getRepository().findAll().isEmpty()) return false;
            ClasificacionTipo obj = new ClasificacionTipo();
                obj.setNombre("Entregado");
                obj.setPonderacion(1);
                controlador.guardar(obj);
            obj = new ClasificacionTipo();
                obj.setNombre("Extra");
                obj.setPonderacion(1+0.5);
                controlador.guardar(obj);
            obj = new ClasificacionTipo();
                obj.setNombre("Retraso");
                obj.setPonderacion(0.5);
                controlador.guardar(obj);
            obj = new ClasificacionTipo();
                obj.setNombre("No entregado");
                obj.setPonderacion(0);
                controlador.guardar(obj);    
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
