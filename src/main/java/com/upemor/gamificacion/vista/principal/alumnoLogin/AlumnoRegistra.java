package com.upemor.gamificacion.vista.principal.alumnoLogin;

import org.springframework.beans.factory.annotation.Autowired;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.MainUI;
import com.upemor.gamificacion.notificaciones.Mailer;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Notification;
import com.vaadin.shared.Position;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class AlumnoRegistra extends Panel{
    private MainUI ui;
    private Label titulo;
    private Button registrar;
    private Button regresar;
    private TextField nombres;
    private TextField apellidos;
    private TextField matricula;
    private TextField correo;
    private TextField palabraClave;
    private PasswordField contrasena;
    
    public AlumnoRegistra(){
        init();
    }
    
    private void init(){
        ui = Element.getUI();
        
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
            
        titulo = new Label("REGISTRAR | ALUMNO");
            Element.cfgComponent(titulo);
            titulo.addStyleNames(ValoTheme.LABEL_H1);
        nombres = new TextField("Nombres");
            Element.cfgComponent(nombres);
            nombres.setPlaceholder("Introduce tu nombre(s): ");
        apellidos = new TextField("Apellidos");
            Element.cfgComponent(apellidos);
            apellidos.setPlaceholder("Introduce tus apellidos: ");
        matricula = new TextField("Matricula");
            Element.cfgComponent(matricula);
            matricula.setPlaceholder("Introduce tu Matricula: ");
            matricula.setMaxLength(10);
        contrasena = new PasswordField("Contraseña");
            Element.cfgComponent(contrasena);
            contrasena.setPlaceholder("Introduce tu Contraseña");
        palabraClave = new TextField("PalabraClave");
            Element.cfgComponent(palabraClave);
            palabraClave.setPlaceholder("Introduce tu Palabra Clave: (Max 15 caracteres) ");
            palabraClave.setMaxLength(15);

        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(titulo);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(nombres);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(apellidos);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(matricula);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(contrasena);
            row1.addColumn().withDisplayRules(12,7,7,7).withComponent(palabraClave);
            
        registrar = new Button("Registrate");
            Element.cfgComponent(registrar);
            registrar.addClickListener((Button.ClickEvent event) -> {eventButtonRegistrar();});
            registrar.setStyleName(ValoTheme.BUTTON_DANGER);

        regresar = new Button("Regresar");
            Element.cfgComponent(regresar);
            regresar.addClickListener((Button.ClickEvent event) -> {eventButtonRegresar();});

        ResponsiveRow row2 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row2,true,true);
            row2.addColumn().withDisplayRules(12,4,3,3).withComponent(registrar);
            row2.addColumn().withDisplayRules(12,4,3,3).withComponent(regresar);
        
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
    
    private void eventButtonRegistrar(){
        try {
            Alumno alumno = new Alumno();
                alumno.setNombres(nombres.getValue());
                alumno.setApellidos(apellidos.getValue());
                alumno.setMatricula(matricula.getValue());
                alumno.setCorreo(matricula.getValue()+"@upemor.edu.mx");
                alumno.setContrasena(contrasena.getValue());
                alumno.setPalabraClave(palabraClave.getValue());
            alumno= ui.getControladorAlumno().guardarRegistro(alumno);
            if (alumno != null) {
                Element.makeNotification("Datos guardados correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.setContent(ui.getFabricaVista().getAlumnoLogin());

            }

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    private void eventButtonRegresar() {
        ui.setContent(ui.getFabricaVista().getAlumnoLogin());
    }
}
