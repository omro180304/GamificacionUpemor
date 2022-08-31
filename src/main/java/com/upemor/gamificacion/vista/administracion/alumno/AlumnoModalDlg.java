package com.upemor.gamificacion.vista.administracion.alumno;

import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.Notification;
import com.vaadin.shared.Position;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import com.vaadin.ui.Slider;
import com.vaadin.ui.themes.ValoTheme;


public class AlumnoModalDlg extends TemplateModalWin<Long>{
    
    private TextField nombres;
    private TextField apellidos;
    private TextField matricula;
    private TextField palabraClave;
    private Slider saldo;
    private Label tituloSaldo;

    
    public AlumnoModalDlg(){
        id = 0l;
        init();
    }
    
    public AlumnoModalDlg(Long id){
        init();
        loadData(id);
                
    }
    
    private void init(){
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        
        nombres = new TextField();
            Element.cfgComponent(nombres,"Nombres");
            nombres.setPlaceholder("Introduce tu(s) nombre(s), sin acentos ");
            
        apellidos = new TextField();
            Element.cfgComponent(apellidos,"Apellidos");
            apellidos.setPlaceholder("Introduce tu(s) apellido(s), sin acentos ");
            
        matricula = new TextField();
            Element.cfgComponent(matricula,"Matricula");
            matricula.setPlaceholder("Introduce tu matricula de alumno");
            matricula.setMaxLength(10);
            
        palabraClave = new TextField();
            Element.cfgComponent(palabraClave,"Palabra clave");
            palabraClave.setPlaceholder("Introduce tu palabra clave de recuperacion ");
            palabraClave.setMaxLength(15);
            
        tituloSaldo = new Label("Saldo");
            Element.cfgComponent(tituloSaldo);
            tituloSaldo.addStyleNames(ValoTheme.LABEL_SMALL);
            
        saldo = new Slider(0,1000);
            saldo.setOrientation(SliderOrientation.HORIZONTAL);
            saldo.setWidth("100%");
            saldo.isResponsive();
            
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(nombres);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(apellidos);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(matricula);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(palabraClave);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(tituloSaldo);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(saldo);
        
        contentLayout.addComponent(content);
        setCaption("Alumnos");
        setWidth("50%");
    }

    @Override
    protected void loadData(Long id){
        try {
            Alumno obj = ui.getControladorAlumno().getRepository().findById(id).get();
            this.id = obj.getId();
            nombres.setValue(obj.getNombres());
            apellidos.setValue(obj.getApellidos());
            matricula.setValue(obj.getMatricula());
            palabraClave.setValue(obj.getPalabraClave());
            saldo.setValue(obj.getSaldo());           
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }  
    
    @Override   
    protected void buttonAcceptEvent() {
        try {
            Alumno obj = new Alumno();
                obj.setId(id);
                obj.setNombres(nombres.getValue());
                obj.setApellidos(apellidos.getValue());
                obj.setCorreo(matricula.getValue()+"@upemor.edu.mx");
                obj.setSaldo(saldo.getValue().intValue());                
                obj.setMatricula(matricula.getValue());
                obj.setPalabraClave(palabraClave.getValue());
        
            obj = ui.getControladorAlumno().guardar(obj);
            if (obj != null) {
                Element.makeNotification("Datos guardados correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getAlumnoDlg().updateDlg();
                close();
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }    
    @Override   protected void buttonDeleteEvent(){}
    @Override   protected void buttonCancelEvent(){ close();}
}
