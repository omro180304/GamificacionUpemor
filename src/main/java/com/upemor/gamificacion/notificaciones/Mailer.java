package com.upemor.gamificacion.notificaciones;

import com.upemor.gamificacion.Gamificacion;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.persistencia.entidad.Canje;
import com.upemor.gamificacion.persistencia.entidad.Premio;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioAlumno;
import com.upemor.gamificacion.persistencia.repositorio.RepositorioUsuario;
import com.upemor.gamificacion.utils.Utils;
import java.io.StringWriter;
import java.util.logging.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class Mailer{
    @Autowired private JavaMailSender mailSender;
    @Autowired private RepositorioUsuario repositorioUsuario;
    @Autowired private RepositorioAlumno repositorioAlumno;
    
    private final String from = "ugamificacion@gmail.com";   
    
    public boolean sendAlumnoWelcomeEmail(Alumno alumno) {
        try {
            VelocityContext context = new VelocityContext();
                context.put("user", alumno.getCorreo());
                context.put("Nombre", alumno.getNombres());
            StringWriter writer = new StringWriter();
            Velocity.setProperty("file.resource.loader.path", Gamificacion.getMainDirectory() + "mailer/");
            Template template = Velocity.getTemplate("userWelcome.vm", "UTF-8");
            template.merge(context, writer);
            MimeMessagePreparator mimeMessagePreparator = (mm) -> {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mm);
                mimeMessageHelper.setTo(alumno.getCorreo());
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject("Bienvenido");
                mimeMessageHelper.setText(writer.toString(), true);
            };
            mailSender.send(mimeMessagePreparator);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            return false;
        }
    }
    
    
    public boolean sendUserPasswordRestoreEmail(Usuario user,String newPassword, String newPalabraClave){
        try{
            VelocityContext context = new VelocityContext();
                context.put("user",user.getNombres());
                context.put("email",user.getCorreo());
                context.put("password",user.getPalabraClave());
                context.put("palabraClave",newPalabraClave);
            StringWriter writer = new StringWriter();
            Velocity.setProperty("file.resource.loader.path",Gamificacion.getMainDirectory()+"mailer/");
            Template template = Velocity.getTemplate("userPasswordRestore.vm","UTF-8");
                template.merge(context,writer);
            MimeMessagePreparator mimeMessagePreparator = (mm) -> {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mm);
                    mimeMessageHelper.setTo(user.getCorreo());
                    mimeMessageHelper.setFrom(from);
                    mimeMessageHelper.setSubject("Contraseña restaurada!!!");
                    mimeMessageHelper.setText(writer.toString(),true);
            };
            mailSender.send(mimeMessagePreparator);
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            return false;
        }
    }
    
    public boolean sendAlumnoPasswordRestoreEmail(Alumno user, String newPassword, String newPalabraClave) {
        try {
            VelocityContext context = new VelocityContext();
                context.put("user", user.getNombres());
                context.put("email", user.getCorreo());
                context.put("password", user.getPalabraClave());
                context.put("palabraClave", newPalabraClave);
            StringWriter writer = new StringWriter();
            Velocity.setProperty("file.resource.loader.path", Gamificacion.getMainDirectory() + "mailer/");
            Template template = Velocity.getTemplate("userPasswordRestore.vm", "UTF-8");
            template.merge(context, writer);
            MimeMessagePreparator mimeMessagePreparator = (mm) -> {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mm);
                mimeMessageHelper.setTo(user.getCorreo());
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject("Contraseña restaurada!!!");
                mimeMessageHelper.setText(writer.toString(), true);
            };
            mailSender.send(mimeMessagePreparator);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            return false;
        }
    }
    
    //Metodo para enviar un correo electronico recive datos del usuario en este caso referentes a un cambio
    //de contrasena
    public boolean sendUsuarioCambioPassEmail(Usuario user,String passwordAnterior, String passwordNuevo, String passwordConfirmado){
        try {
            VelocityContext context = new VelocityContext();
            context.put("user", user.getNombres());
            context.put("email", user.getCorreo());
            context.put("password", user.getContrasena());
            context.put("palabraClave", passwordAnterior);
            StringWriter writer = new StringWriter();
            Velocity.setProperty("file.resource.loader.path", Gamificacion.getMainDirectory() + "mailer/");
            Template template = Velocity.getTemplate("userCambioPass.vm", "UTF-8");
            template.merge(context, writer);
            MimeMessagePreparator mimeMessagePreparator = (mm) -> {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mm);
                mimeMessageHelper.setTo(user.getCorreo());
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject("Contraseña restaurada!!!");
                mimeMessageHelper.setText(writer.toString(), true);
            };
            mailSender.send(mimeMessagePreparator);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            return false;
        }
    }
    
    public boolean sendAlumnoCambioPassEmail(Alumno user,String passwordAnterior, String passwordNuevo, String passwordConfirmado){
        try{
            VelocityContext context = new VelocityContext();
            context.put("user", user.getNombres());
            context.put("email", user.getCorreo());
            context.put("passwordNuevo", user.getContrasena());
            context.put("passwordAnterior", passwordAnterior);
            StringWriter writer = new StringWriter();
            Velocity.setProperty("file.resource.loader.path",Gamificacion.getMainDirectory()+"mailer/");
            Template template = Velocity.getTemplate("userCambioPass.vm","UTF-8");
                template.merge(context,writer);
            MimeMessagePreparator mimeMessagePreparator = (mm) -> {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mm);
                    mimeMessageHelper.setTo(user.getCorreo());
                    mimeMessageHelper.setFrom(from);
                    mimeMessageHelper.setSubject("Cambio de Contraseña por el Alumno");
                    mimeMessageHelper.setText(writer.toString(),true);
            };
            mailSender.send(mimeMessagePreparator);
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            return false;
        }
    }

    public boolean sendAlumnoCanjeEmail(Canje canje, Usuario user,Alumno alumno,Premio premio) {
        try {
            VelocityContext context = new VelocityContext();
            context.put("alumno", alumno.getNombres());
            context.put("alumnoape", alumno.getApellidos());
            context.put("premio", premio.getNombre());
            context.put("materia", premio.getObjMateria().toString());
            context.put("profesor", user.getNombres());
            
            StringWriter writer = new StringWriter();
            Velocity.setProperty("file.resource.loader.path", Gamificacion.getMainDirectory() + "mailer/");
            Template template = Velocity.getTemplate("usuarioCanje.vm", "UTF-8");
            template.merge(context, writer);
            MimeMessagePreparator mimeMessagePreparator = (mm) -> {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mm);
                mimeMessageHelper.setTo(user.getCorreo());
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject("Canje Completado");
                mimeMessageHelper.setText(writer.toString(), true);
            };
            mailSender.send(mimeMessagePreparator);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            return false;
        }
    }
}
