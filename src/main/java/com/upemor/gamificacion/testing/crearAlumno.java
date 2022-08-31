package com.upemor.gamificacion.testing;

import com.upemor.gamificacion.controladores.ControladorAlumno;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.utils.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class crearAlumno{
    @Autowired private ControladorAlumno controlador;
    
    public boolean crearDatos()throws Exception{
        try{
            if(!controlador.getRepository().findAll().isEmpty()) return false;
            Alumno obj = new Alumno();
                obj.setApellidos("Oviedo");
                obj.setContrasena("1234");
                obj.setNombres("Rocio");
                obj.setPalabraClave("Palabraclave");
                obj.setMatricula("omro180304".toUpperCase());
                obj = controlador.guardar(obj);
              //  controlador.cambiarPassword(obj,obj.getMatricula(),"1234567890","1234567890");
            obj = new Alumno();
                obj.setApellidos("Bahena Solis");
                obj.setContrasena("12345");
                obj.setNombres("Jose Saul");
                obj.setPalabraClave("Palabraclave");
                obj.setMatricula("bsjo181077".toUpperCase());
                obj = controlador.guardar(obj);
               // controlador.cambiarPassword(obj,obj.getMatricula(),"1234567890","1234567890");
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
