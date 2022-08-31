package com.upemor.gamificacion.controladores;

import com.upemor.gamificacion.persistencia.repositorio.RepositorioAlumno;
import org.springframework.beans.factory.annotation.Autowired;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.notificaciones.Mailer;
import org.springframework.stereotype.Component;
import com.upemor.gamificacion.utils.Utils;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ControladorAlumno extends Controlador<Alumno,RepositorioAlumno> {
    @Autowired private Mailer mailer;
    @Override
    //Metodo que hace la validacion de datos antes de ser almacenados
    //genera una excepcion en caso de que alguna de las validaciones 
    //no se cumpla y devuelve true cuando se cumple con las sentencias
    protected boolean validar(Alumno obj) throws Exception {
        try {
            if (obj.getNombres().isEmpty()) {throw new Exception("Nombre no valido");}
            if (contieneSoloLetras(obj.getNombres())== false){throw new Exception("Nombre(s) no valido, solo letras");}
            if (obj.getApellidos().isEmpty()) {throw new Exception("Apellidos no valido");}
            if (contieneSoloLetras(obj.getApellidos())== false){throw new Exception("Apellidos no validos, solo letras !");}
            if (obj.getCorreo().isEmpty()) {throw new Exception("Correo no valido");}
            if (obj.getPalabraClave().isEmpty()) {throw new Exception("Palabra Clave no valido");}
            if (obj.getMatricula().isEmpty()) {throw new Exception("Matricula no valido");}
            if (obj.getSaldo() < 0) {throw new Exception("Saldo no valido");}
            
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }

    // Metodo que valida si la cadena intoducida contiene solo letras
    //falso si la cadena contiene numeros o simbolos
    //true si la cadena solo contiene letras
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
    
    //Metodo que hace el almacenamiento de los datos del alumno por el profesor/administrados
    // genera el id en caso de no existir el registro y salva los cambios en caso de haber
    @Override public Alumno guardar(Alumno newer) throws Exception {
        try{
            if (validar(newer)) {
                if (newer.getId() == 0) {
                    String contraseniaTemporal = newer.getMatricula();
                        newer.setContrasena(Utils.encryptMD5(contraseniaTemporal));
                    return getRepository().save(newer);
                }
                Alumno original = getRepository().findById(newer.getId()).get();
                    original.setNombres(newer.getNombres());
                    original.setApellidos(newer.getApellidos());
                    original.setMatricula(newer.getMatricula());
                    original.setCorreo(newer.getCorreo());
                    original.setPalabraClave(newer.getPalabraClave());
                    original.setSaldo(newer.getSaldo());    
                return getRepository().save(original);
            }
        }catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
                throw ex;
        }
        return null;
    }
    
    //Metodo que hace el almacenamiento de los datos del alumno desde el registro hecho por el alumno
    // devuelve el registro almacenado 
    public Alumno guardarRegistro(Alumno newer) throws Exception {
        try {
            if (validar(newer)) {
                if (newer.getId() == 0) { 
                    String contra = newer.getContrasena();
                        newer.setContrasena(Utils.encryptMD5(contra));                          
                    return getRepository().save(newer);
                }
                Alumno alumno = getRepository().getOne(newer.getId());
                    alumno.setNombres(newer.getNombres());
                    alumno.setApellidos(newer.getApellidos());
                    alumno.setMatricula(newer.getMatricula());
                    alumno.setCorreo(newer.getCorreo());
                    alumno.setPalabraClave(newer.getPalabraClave());
                    alumno.setSaldo(0);
                mailer.sendAlumnoWelcomeEmail(alumno);
                return getRepository().save(alumno);
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
    
    //Metodo que hace el almacenamiento de los datos del alumno ingresados desde un archivo excel.csv
    public boolean importarDesdeCSV(String archivo,String separador) throws Exception{
        try{
            //lectura del archivo linea por linea
            List<String> listData = Files.readAllLines(Paths.get(archivo));
            listData.remove(0);
            //recorre los datos y separa la informacion gracias al separador 
            for (String data : listData) {
                String dts[] = data.split(separador);
                Alumno alumno = getRepository().getByMatricula(dts[2]);
                if (alumno== null ) {
                    //asigna la informacion a la variable para almacenar
                    alumno = new Alumno();
                    alumno.setMatricula(dts[2]);
                    alumno.setCorreo(dts[2] + "@upemor.edu.mx");
                    alumno.setSaldo(0);
                    alumno.setPalabraClave(dts[2]);
                    alumno.setContrasena(Utils.encryptMD5(dts[2]));//encripta la contrasena
                }
                alumno.setNombres(dts[0]);
                alumno.setApellidos(dts[1]);             
                getRepository().save(alumno);//guarda la informacion
            }
            return true;//devuelve true si se realizo el amacenamiento del registro
        }catch (Exception ex) {//genera una excepcion en caso de que no y muestra error en consola
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
    
    //Metodo que hace la validacion de excepciones del usuario Alumno
    //En caso de que los datos entren en una expepcion no inicia sesion
    public Alumno login(String correo,String password) throws Exception{
        try{
            if(correo.isBlank()){throw new Exception("Favor de proporcionar el correo");}
            if(password.isBlank()){throw new Exception("Favor de proporcionar la contraseña");}
            Alumno user = getRepository().getByCorreo(correo);
            if(user == null){throw new Exception("Las credenciales son incorrectas");}
            String newPassword = Utils.encryptMD5(password);
            if(!user.getContrasena().equals(newPassword)){throw new Exception("Las credenciales son incorrectas");}
            return user;
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
    
    //Metodo que hace la validacion de excepciones para validar la recuperacion de contrasena
    //Realiza el cambio de contrasena por la palabra Clave y envia un correo con los nuevos datos como aviso
    public Alumno recuperarContrasena(String correo,String palabraClave, String palabraClaveNueva) throws Exception{
        try{
            if(correo.isBlank()){throw new Exception("Favor de proporcionar el correo");}
            if(palabraClave.isBlank()){throw new Exception("Favor de proporcionar la palabra clave");}
            if(palabraClaveNueva.isBlank()){throw new Exception("Favor de proporcionar la Nueva Palabra clave");}
            Alumno user = getRepository().getByCorreo(correo);
            if(user == null){throw new Exception("Las credenciales son incorrectas");}
            if(user.getPalabraClave().equals(palabraClaveNueva)){throw new Exception("La palabra clave nueva es la misma que la anterior");}
            String newPalabraClave = palabraClave;
            if (!user.getPalabraClave().equals(newPalabraClave)) {throw new Exception("Las credenciales son incorrectas");}
            Alumno obj = getRepository().getOne(user.getId());
                obj.setContrasena(Utils.encryptMD5(palabraClave));
                obj.setPalabraClave(palabraClaveNueva);
            getRepository().save(obj);
            mailer.sendAlumnoPasswordRestoreEmail(user, palabraClave, palabraClaveNueva);
            return user;
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
    
    //Metodo que valida las excepciones para realizar el cambio de contrasena
    //Envia un correo con los nuevos datos como aviso
    public Alumno cambiarPassword(Alumno alumno,String passwordAnterior,String passwordNuevo,String passwordConfirmado) throws Exception{
        try{
            // validaciones para hacer el cambio
            if(passwordAnterior.isBlank()){throw new Exception("Favor de proporcionar la contraseña anterior");}
            if(passwordNuevo.isBlank()){throw new Exception("Favor de proporcionar la contraseña nueva");}
            if(passwordConfirmado.isBlank()){throw new Exception("Favor de confirmar la nueva contraseña");}
            if(!passwordConfirmado.equals(passwordNuevo)){throw new Exception("La confirmación del nuevo password es incorreta");}
            //obtener datos del usuario
            Alumno user = getRepository().getOne(alumno.getId());
            if(!user.getContrasena().equals(Utils.encryptMD5(passwordAnterior))){throw new Exception("El password anterior no es valido");}
            //reemplazo de la antigua por la nueva contrase
            user.setContrasena(Utils.encryptMD5(passwordNuevo));
            getRepository().save(user);//guarda cambios
            //llamada al metodo que hara aviso mediante un correo electronico al usuario
            mailer.sendAlumnoCambioPassEmail(user, passwordAnterior, passwordNuevo, passwordConfirmado);
            return user;

        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}
