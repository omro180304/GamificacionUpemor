package com.upemor.gamificacion.vista.principal.usuarioLogin;

import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.themes.ValoTheme;
import com.upemor.gamificacion.MainUI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Notification;
import com.vaadin.shared.Position;
import java.util.logging.Logger;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class UsuarioRecupera extends Panel{
    private MainUI ui;
    private Button accept;
    private TextField correo;
    private PasswordField palabraClave;
    private PasswordField palabraClaveNueva;
    private Button regresar;
    private Label titulo;
    
    public UsuarioRecupera(){
        init();
    }
    
    private void init(){
        ui = Element.getUI();
        
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        
        titulo = new Label("Recuperación de contraseña Usuario");
            Element.cfgComponent(titulo);
            titulo.addStyleNames(ValoTheme.LABEL_H2);
            
        correo = new TextField("Correo");
            Element.cfgComponent(correo);
            correo.setPlaceholder("Introduce tu Correo: @upemor.edu.mx");
            
        palabraClave = new PasswordField("Palabra Clave");
            Element.cfgComponent(palabraClave);
            palabraClave.setPlaceholder("Introduce tu palabra clave");
            palabraClave.setMaxLength(15);
            
        palabraClaveNueva = new PasswordField("Nueva Palabra Clave");
            Element.cfgComponent(palabraClaveNueva);
            palabraClaveNueva.setMaxLength(15);
            palabraClaveNueva.setPlaceholder("Introduce tu palabra clave nueva (Max 15 caracteres)");
                
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(titulo);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(correo);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(palabraClave);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(palabraClaveNueva);
        
        accept = new Button("Recuperar");
            Element.cfgComponent(accept);
            accept.addClickListener((Button.ClickEvent event) -> {eventButtonAccept();});
            accept.setStyleName(ValoTheme.BUTTON_DANGER);
        regresar = new Button("Regresar");
            Element.cfgComponent(regresar);
            regresar.addClickListener((Button.ClickEvent event) -> {eventButtonRegresar();});
      
        ResponsiveRow row2 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row2,true,true);
            row2.addColumn().withDisplayRules(12,4,3,3).withComponent(accept);
            row2.addColumn().withDisplayRules(12,4,3,3).withComponent(regresar);

        
        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(content);
            main.setComponentAlignment(content,Alignment.MIDDLE_CENTER);
            
        setWidth("100%");
        setHeight("100%");
        setContent(main);
        setCaptionAsHtml(true);
    }
    
    private void eventButtonAccept(){
        try{
            Usuario user = ui.getControladorUsuario().recuperarContrasena(correo.getValue(),palabraClave.getValue(),palabraClaveNueva.getValue());
            if(user != null){
                ui.setUsuario(user);
                Element.makeNotification("Contraseña reemplazada por la palabra clave", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.setContent(ui.getFabricaVista().getUsuarioVista());
            }
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    private void eventButtonRegresar() {
        ui.setContent(ui.getFabricaVista().getAlumnoLogin());
    }
}
