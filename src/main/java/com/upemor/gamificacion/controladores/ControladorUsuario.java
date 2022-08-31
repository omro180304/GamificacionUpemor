package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.notificaciones.Mailer;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioUsuario;
import com.upemor.gamificacion.utils.Utils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ControladorUsuario extends Controlador<Usuario,RepositorioUsuario> {
    @Autowired private Mailer mailer;
    @Override
    protected boolean validar(Usuario obj) throws Exception {
        try {
            if (obj.getNombres().isEmpty()) {throw new Exception("Nombre(s) no valido");}
            if (contieneSoloLetras(obj.getNombres())== false){throw new Exception("Nombre(s) no valido, solo letras");}
            if (obj.getApellidos().isEmpty()) {throw new Exception("Apellidos no validos");}
            if (contieneSoloLetras(obj.getApellidos()) == false) {throw new Exception("Apellidos no validos, solo letras");}
            if (obj.getCorreo().isEmpty()) {throw new Exception("Correo no valido");}
            if (obj.getPalabraClave().isEmpty()) {throw new Exception("Palabra Clave no valido");}
            if (obj.getUsuarioTipo() <= 0) {throw new Exception("Tipo de usuario no valido");}
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
    public Usuario guardar(Usuario newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0){
                    String contraseniaTemporal = newer.getPalabraClave();
                        newer.setContrasena(Utils.encryptMD5(contraseniaTemporal));
                    return getRepository().save(newer);
                }
                Usuario original = getRepository().findById(newer.getId()).get();
                    original.setNombres(newer.getNombres());
                    original.setApellidos(newer.getApellidos());
                    original.setCorreo(newer.getCorreo());
                    original.setPalabraClave(newer.getPalabraClave());
                    original.setUsuarioTipo(newer.getUsuarioTipo());
                return getRepository().save(original);
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
    
    public Usuario cambiarPassword(Usuario usuario,String passwordAnterior,String passwordNuevo,String passwordConfirmado) throws Exception{
        try{
            if (passwordAnterior.isBlank()) {throw new Exception("Favor de proporcionar la contraseña anterior");}
            if (passwordNuevo.isBlank()) {throw new Exception("Favor de proporcionar la contraseña nueva");}
            if (passwordConfirmado.isBlank()) {throw new Exception("Favor de confirmar la nueva contraseña");}
            if (!passwordConfirmado.equals(passwordNuevo)) {throw new Exception("La confirmación del nuevo password es incorreta");}
            Usuario user = getRepository().getOne(usuario.getId());
            if (!user.getContrasena().equals(Utils.encryptMD5(passwordAnterior))) {throw new Exception("El password anterior no es valido");}
            user.setContrasena(Utils.encryptMD5(passwordNuevo));
            getRepository().save(user);
            mailer.sendUsuarioCambioPassEmail(user, passwordAnterior, passwordNuevo, passwordConfirmado);
            return user;
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
    
    public Usuario login(String usuario,String password) throws Exception{
        try{
            if(usuario.isBlank()){throw new Exception("Favor de proporcionar las credenciales");}
            if(password.isBlank()){throw new Exception("Favor de proporcionar las credenciales");}
            Usuario user = getRepository().getByCorreo(usuario);
            if(user == null){throw new Exception("Las credenciales son incorrectas");}
            String newPassword = Utils.encryptMD5(password);
            if(!user.getContrasena().equals(newPassword)){throw new Exception("Las credenciales son incorrectas");}
            return user;
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
    
    public Usuario recuperarContrasena(String correo,String palabraClave, String palabraClaveNueva) throws Exception{
        try{
            if(correo.isBlank()){throw new Exception("Favor de proporcionar el correo");}
            if(palabraClave.isBlank()){throw new Exception("Favor de proporcionar la palabra clave");}
            if(palabraClaveNueva.isBlank()){throw new Exception("Favor de proporcionar la Nueva Palabra clave");}
            Usuario user = getRepository().getByCorreo(correo);
            if(user == null){throw new Exception("Las credenciales son incorrectas");}
            if(user.getPalabraClave().equals(palabraClaveNueva)){throw new Exception("La palabra clave nueva es la misma que la anterior");}
            String newPalabraClave = palabraClave;
            if (!user.getPalabraClave().equals(newPalabraClave)) {throw new Exception("Las credenciales son incorrectas");}
            //if(!user.getPalabraClave()(palabraClaveNueva)){}
            Usuario obj = getRepository().getOne(user.getId());
            obj.setContrasena(Utils.encryptMD5(palabraClave));
            obj.setPalabraClave(palabraClaveNueva);
            getRepository().save(obj);
            mailer.sendUserPasswordRestoreEmail(user, palabraClave, palabraClaveNueva);
            return user;
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
    
}

