package com.upemor.gamificacion.vista.principal.alumnoLogin;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.themes.ValoTheme;
import com.upemor.gamificacion.MainUI;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlumnoLogin extends Panel{
    private MainUI ui;
    
    private Button accept;
    private Button regresar;
    private Button recuperar;
    private Button registrar;
    private TextField correo;
    private PasswordField password;
    private Label titulo;
    private Image icono;
    
    public AlumnoLogin(){
        init();
    }
    
    private void init(){
        ui = Element.getUI();
        
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
            
        titulo = new Label("Inicio de sesión | Alumno");
            Element.cfgComponent(titulo);
            titulo.setSizeFull();
            titulo.addStyleNames(ValoTheme.LABEL_H1);
        correo = new TextField("Correo");
            Element.cfgComponent(correo);
            correo.setPlaceholder("Introduce tu Correo: @upemor.edu.mx");
            correo.setWidth("100%");
        password = new PasswordField("Password");
            Element.cfgComponent(password);
            password.setPlaceholder("Introduce tu Contraseña");
            
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row1,true,true);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(titulo);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(correo);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(password);
        
        accept = new Button("Iniciar Sesión");
            Element.cfgComponent(accept);
            accept.addClickListener((Button.ClickEvent event) -> {eventButtonAccept();});
            accept.setStyleName(ValoTheme.BUTTON_DANGER);
            
        recuperar = new Button("Recuperar Contraseña");
            Element.cfgComponent(recuperar);
            recuperar.setStyleName(ValoTheme.BUTTON_PRIMARY);
            recuperar.addClickListener((Button.ClickEvent event) -> {eventButtonRecuperar();});
        
        registrar = new Button("Registrarte");
            Element.cfgComponent(registrar);
            registrar.addClickListener((Button.ClickEvent event) -> {eventButtonRegistrar();});
            registrar.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        
        regresar = new Button("Regresar", VaadinIcons.ARROW_BACKWARD);
            Element.cfgComponent(regresar);
            regresar.addClickListener((Button.ClickEvent event) -> {eventButtonRegresar();});
            regresar.setSizeFull();

        ResponsiveRow row2 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row2,true,true);
            row2.addColumn().withDisplayRules(12,7,7,7).withComponent(accept);
            row2.addColumn().withDisplayRules(12,7,7,7).withComponent(recuperar);
            row2.addColumn().withDisplayRules(12,7,7,7).withComponent(registrar);
            row2.addColumn().withDisplayRules(12,7,7,7).withComponent(regresar);
        
        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(content);
            main.setComponentAlignment(content,Alignment.MIDDLE_CENTER);
            
        setWidth("100%");
        setHeight("100%");
        setContent(main);
        setCaptionAsHtml(true);
        setCaption("");
    }
    
    private void eventButtonAccept(){
        try{
            Alumno user = ui.getControladorAlumno().login(correo.getValue(),password.getValue());
            if(user != null){
                ui.setAlumno(user);
                ui.setContent(ui.getFabricaVista().getAlumnoVista());
            }
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    private void eventButtonRecuperar(){
        ui.setContent(ui.getFabricaVista().getAlumnoRecupera());  
    }
    
    private void eventButtonRegresar() {
        ui.setContent(ui.getFabricaVista().getInicialDlg());
    }

    private void eventButtonRegistrar() {
        ui.setContent(ui.getFabricaVista().getAlumnoRegistra());
    }
}
