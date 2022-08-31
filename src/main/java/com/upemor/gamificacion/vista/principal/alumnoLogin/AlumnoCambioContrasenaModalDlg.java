package com.upemor.gamificacion.vista.principal.alumnoLogin;

import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Notification;
import com.vaadin.shared.Position;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import lombok.Setter;


public class AlumnoCambioContrasenaModalDlg extends TemplateModalWin<Long>{
    
    @Setter
    private AlumnoCambioContrasenaModalDlg alumnoCambioContrasenaModalDlg;
    private Alumno alumno;
    
    private PasswordField passwordAnterior;
    private PasswordField passwordNuevo;
    private PasswordField passwordConfirmado;
    private Button accept;
    private Label titulo;
    
    public AlumnoCambioContrasenaModalDlg(){
        init();
    }   
    
    public AlumnoCambioContrasenaModalDlg(Long id){
        this.alumno = alumno;
        init(); 
    }
    
    private void init(){
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        
        titulo = new Label("Cambio de contraseña | Alumo");
            Element.cfgComponent(titulo);
            titulo.addStyleNames(ValoTheme.LABEL_H1);
        passwordAnterior = new PasswordField("Contraseña Actual");
            Element.cfgComponent(passwordAnterior);
            passwordAnterior.setPlaceholder("Introduce tu Contraseña Actual"); 
            passwordAnterior.setMaxLength(25);
        passwordNuevo = new PasswordField("Nueva Contraseña");
            Element.cfgComponent(passwordNuevo);
            passwordNuevo.setPlaceholder("Introduce tu Nueva Contraseña");
            passwordNuevo.setMaxLength(25);
        passwordConfirmado = new PasswordField("Confirma Nueva Contraseña");
            Element.cfgComponent(passwordConfirmado);
            passwordConfirmado.setMaxLength(25);
            passwordConfirmado.setPlaceholder("Introduce tu Nueva Contraseña de Nuevo");
            
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(titulo);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(passwordAnterior);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(passwordNuevo);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(passwordConfirmado);
        
        contentLayout.addComponent(content);
        setWidth("50%");
    }

    @Override
    protected void loadData(Long id){
        try {
            Alumno obj = ui.getControladorAlumno().getRepository().findById(id).get();
            this.id = obj.getId();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }  
    
    @Override   
    protected void buttonAcceptEvent() {
        try {
            Alumno user = ui.getControladorAlumno().cambiarPassword(ui.getAlumno(),passwordAnterior.getValue(), passwordNuevo.getValue(),passwordConfirmado.getValue());
            if (user != null) {
                ui.setAlumno(user);
                ui.setContent(ui.getFabricaVista().getAlumnoVista());
            }
            Element.makeNotification("Datos guardados correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());    
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    @Override   protected void buttonDeleteEvent(){}
    @Override   protected void buttonCancelEvent(){ close();}
}
