package com.upemor.gamificacion.vista.principal.usuarioLogin;

import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.themes.ValoTheme;
import com.upemor.gamificacion.MainUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Notification;
import com.vaadin.shared.Position;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Label;

public class UsuarioLogin extends Panel{
    private MainUI ui;
    private Image icono;
    private Label titulo;
    private Button accept;
    private Button regresar;
    private Button recuperar;
    private TextField usuario;
    private PasswordField password;
    
    public UsuarioLogin(){
        init();
    }
    
    private void init(){
        ui = Element.getUI();
        
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
            content.setHeightFull();
            content.setWidthFull();
                
        icono = new Image("",new ThemeResource("images/iconoMaestro.png"));
            Element.cfgComponent(icono);
            icono.setWidth("10%");
        titulo = new Label("Inicio de sesión Profesor/Administrador");
            Element.cfgComponent(titulo);
            titulo.addStyleNames(ValoTheme.LABEL_H1);     
        usuario = new TextField("Correo");
            Element.cfgComponent(usuario);
            usuario.setPlaceholder("Introduce tu Correo");
        password = new PasswordField("Password");
            Element.cfgComponent(password);
            password.setPlaceholder("Introduce tu Contraseña");
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.MIDDLE_CENTER);
            Element.cfgLayoutComponent(row1,true,true);
            
            row1.addColumn().withDisplayRules(12,7,7,9).withComponent(titulo);
            row1.addColumn().withDisplayRules(12,4,3,7).withComponent(usuario);
            row1.addColumn().withDisplayRules(12,4,3,7).withComponent(password);
        
        accept = new Button("Iniciar Sesión");
            Element.cfgComponent(accept);
            accept.addClickListener((Button.ClickEvent event) -> {eventButtonAccept();});
            accept.setStyleName(ValoTheme.BUTTON_DANGER);
            
        recuperar = new Button("Recuperar Contraseña");
            Element.cfgComponent(recuperar);
            recuperar.addClickListener((Button.ClickEvent event) -> {eventButtonRecuperar();});
            recuperar.setStyleName(ValoTheme.BUTTON_PRIMARY);

        regresar = new Button("Regresar", VaadinIcons.ARROW_BACKWARD);
            Element.cfgComponent(regresar);
            regresar.addClickListener((Button.ClickEvent event) -> {eventButtonRegresar();});
            regresar.setSizeFull();

        ResponsiveRow row2 = content.addRow().withAlignment(Alignment.MIDDLE_CENTER);
            Element.cfgLayoutComponent(row2,true,true);
            row2.addColumn().withDisplayRules(12,4,3,7).withComponent(accept);
            row2.addColumn().withDisplayRules(12,4,3,7).withComponent(recuperar);
            row2.addColumn().withDisplayRules(12,4,3,7).withComponent(regresar);
        
        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(content);
            main.setComponentAlignment(content,Alignment.MIDDLE_CENTER);
            
        setHeightFull();
        setWidthFull();
        setContent(main);
        setCaptionAsHtml(true);
    }
    
    private void eventButtonAccept(){
        try{
            Usuario user = ui.getControladorUsuario().login(usuario.getValue(),password.getValue());
            if(user != null){
                ui.setUsuario(user);
                ui.setContent(ui.getFabricaVista().getUsuarioVista());
            }
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    private void eventButtonRegresar() {
        ui.setContent(ui.getFabricaVista().getInicialDlg());
    }
    
    private void eventButtonRecuperar() {
        ui.setContent(ui.getFabricaVista().getUsuarioRecupera());
    }
}
