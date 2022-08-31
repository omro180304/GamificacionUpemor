
package com.upemor.gamificacion.testing;

import com.upemor.gamificacion.controladores.ControladorUsuario;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class crearUsuario{
    @Autowired private ControladorUsuario controlador;
    
    public boolean crearDatos()throws Exception{
        try{
            if(!controlador.getRepository().findAll().isEmpty()) return false;
            Usuario obj = new Usuario();
                obj.setApellidos("user");
                obj.setContrasena("12345");
                obj.setCorreo("admin@upemor.edu.mx");
                obj.setNombres("Admin");
                obj.setPalabraClave("Palabraclave");
                obj.setUsuarioTipo(1);    
                obj = controlador.guardar(obj);
               // controlador.cambiarPassword(obj,obj.getPalabraClave(),"1234567890","1234567890");
            obj = new Usuario();
                obj.setApellidos("Lopez");
                obj.setContrasena("123456");
                obj.setCorreo("maestro@gmail.com");
                obj.setNombres("Juan");
                obj.setPalabraClave("Palabraclave");
                obj.setUsuarioTipo(1);    
                obj = controlador.guardar(obj);
                //controlador.cambiarPassword(obj,obj.getPalabraClave(),"1234567890","1234567890");
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
