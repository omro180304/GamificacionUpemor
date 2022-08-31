package com.upemor.gamificacion.vista.principal;

import com.upemor.gamificacion.vista.principal.usuarioLogin.UsuarioLogin;
import com.upemor.gamificacion.vista.principal.alumnoLogin.AlumnoLogin;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.server.ThemeResource;
import com.upemor.gamificacion.MainUI;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
//Autores: 
public class InicialDlg extends Panel{
    
    private MainUI ui;

    private Image loginProfesor ;
    private Image loginAlumno ;
    private Label titulo;

    
    public InicialDlg(){
        init();
    }
    
    private void init(){
        ui = Element.getUI();
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        
        Image image = new Image("",new ThemeResource("images/logo.png"));
           image.setWidth("50%");          
        titulo = new Label("Selecciona el Tipo de Usuario para acceder: ");
            Element.cfgComponent(titulo);
            titulo.setWidth("80%");
            titulo.addStyleNames(ValoTheme.LABEL_H1);

        loginAlumno = new Image(null, new ThemeResource("images/iconoAlumno.png"));
            Element.cfgComponent(loginAlumno);
            loginAlumno.setWidth("30%");
            loginAlumno.addClickListener(event -> {eventButtonLoginAlumno();});
        
        loginProfesor = new Image(null, new ThemeResource("images/iconoProfesor.png"));
            Element.cfgComponent(loginProfesor);
            loginProfesor.setWidth("30%");
            loginProfesor.addClickListener(event -> {eventButtonLoginProfesor();});
        
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.TOP_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,6,6,9).withComponent(image);
            row1.addColumn().withDisplayRules(12,5,5,9).withComponent(titulo);
            row1.addColumn().withDisplayRules(12,6,5,5).withComponent(loginProfesor);
            row1.addColumn().withDisplayRules(12,5,5,4).withComponent(loginAlumno);
        
        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(content);
        setSizeFull();
        setContent(main);
        setCaptionAsHtml(true);
    }
    
    private void eventButtonLoginProfesor(){
        Element.makeNotification("Seleccionaste el boton de Login profesor", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        ui.setContent(new UsuarioLogin());
    }
    
    private void eventButtonLoginAlumno(){
        Element.makeNotification("Seleccionaste el boton de Login alumno", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        ui.setContent(new AlumnoLogin());
    }
}