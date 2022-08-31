package com.upemor.gamificacion.vista.principal.usuarioLogin;

import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.PasswordField;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import java.util.logging.Level;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import lombok.Setter;


public class UsuarioCambioContrasenaModalDlg extends TemplateModalWin<Long>{
    
    @Setter
    private UsuarioCambioContrasenaModalDlg usuarioCambioContrasenaModalDlg;
    private Usuario usuario;
    
    private PasswordField passwordAnterior;
    private PasswordField passwordNuevo;
    private PasswordField passwordConfirmado;
    private Button accept;
    private Label titulo;
    
    public UsuarioCambioContrasenaModalDlg(){
        init();
    }   
    
    public UsuarioCambioContrasenaModalDlg(Long id){
        this.usuario = usuario;
        init(); 
    }
    
    private void init(){
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
            
        titulo = new Label("Cambio de contraseña | Profesor/Administrador ");
            Element.cfgComponent(titulo);
            titulo.addStyleNames(ValoTheme.LABEL_BOLD,ValoTheme.LABEL_LIGHT,ValoTheme.LABEL_H3);
        passwordAnterior = new PasswordField("Contraseña Actual");
            Element.cfgComponent(passwordAnterior);
            passwordAnterior.setPlaceholder("Introduce tu Contraseña Actual");
            
        passwordNuevo = new PasswordField("Nueva Contraseña");
            Element.cfgComponent(passwordNuevo);
            passwordNuevo.setPlaceholder("Introduce tu Nueva Contraseña");
            
        passwordConfirmado = new PasswordField("Confirma Nueva Contraseña");
            Element.cfgComponent(passwordConfirmado);
            passwordConfirmado.setPlaceholder("Introduce tu Nueva Contraseña de Nuevo");
            
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(titulo);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(passwordAnterior);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(passwordNuevo);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(passwordConfirmado);
        
        contentLayout.addComponent(content);
        //setCaption("Usuarios");
        setWidth("50%");
    }

    @Override
    protected void loadData(Long id){
        try {
            Usuario obj = ui.getControladorUsuario().getRepository().findById(id).get();
            this.id = obj.getId();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }  
    
    @Override   
    protected void buttonAcceptEvent() {
        try {
            Usuario user = ui.getControladorUsuario().cambiarPassword(ui.getUsuario(),passwordAnterior.getValue(), passwordNuevo.getValue(),passwordConfirmado.getValue());
            if (user != null) {
                ui.setUsuario(user);
                ui.setContent(ui.getFabricaVista().getUsuarioVista());
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
