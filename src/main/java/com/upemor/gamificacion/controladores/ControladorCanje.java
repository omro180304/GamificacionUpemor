package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.notificaciones.Mailer;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioCanje;
import org.springframework.beans.factory.annotation.Autowired;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.persistencia.entidad.Premio;
import com.upemor.gamificacion.persistencia.entidad.Canje;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ControladorCanje extends Controlador<Canje,RepositorioCanje> {
    @Autowired private ControladorAlumno controladorAlumno;
    @Autowired private ControladorPremio controladorPremio;
    @Autowired private ControladorUsuario controladorUsuario;
    @Autowired private Mailer mailer;

    @Override
    protected boolean validar(Canje obj) throws Exception {
        try {
            if(obj.getAlumno() <= 0 ) { throw new Exception("Alumno no valida");}
            if (obj.getPremio() <= 0) {throw new Exception("Premio no valida");}
            if (obj.getPrecio() <= 0) {throw new Exception("Precio no valida");}
            Alumno alumno = controladorAlumno.getRepository().getOne(obj.getAlumno());
            if (alumno.getSaldo() < obj.getPrecio()) {throw new Exception("Saldo insuficiente");}
            Premio premio = controladorPremio.getRepository().getOne(obj.getPremio());
            if (premio.getStock() <= 0) {throw new Exception("Stock no disponible");}
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Canje guardar(Canje canje) throws Exception {
        try {
            if (validar(canje)) {
                if (canje.getId() == 0) {
                    Alumno alumno = controladorAlumno.getRepository().getOne(canje.getAlumno());
                    alumno.setSaldo(alumno.getSaldo()- canje.getPrecio());
                    Premio premio = controladorPremio.getRepository().getOne(canje.getPremio());
                    premio.setStock(premio.getStock()- 1);
                    alumno=controladorAlumno.guardar(alumno);
                    premio=controladorPremio.guardar(premio);
                    System.out.println(canje.getObjPremio());
                    Usuario user = controladorUsuario.getRepository().getOne(premio.getProfesor());
                    mailer.sendAlumnoCanjeEmail(canje,user,alumno,premio);
                    return getRepository().save(canje);
                }
                Canje original = getRepository().findById(canje.getId()).get();
                return getRepository().save(original);
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }   
    

    
}
