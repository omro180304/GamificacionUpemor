package com.upemor.gamificacion.vista.administracion.materia.unidad.actividad;

import com.upemor.gamificacion.persistencia.entidad.Actividad;
import com.upemor.gamificacion.persistencia.entidad.Unidad;
import com.upemor.gamificacion.utils.FileUploadReceiver;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Upload;
import lombok.Setter;

public class ActividadModalDlg extends TemplateModalWin<Long>{
    
    @Setter private ActividadDlg actividadDlg;
    @Setter private Unidad unidad;
    
    private FileUploadReceiver fileUploadReceiver;
    private Label tituloPuntaje;
    private Upload fileUpload;
    private TextField nombre;
    private Button agregar;
    private Slider puntaje;
   
    public ActividadModalDlg(){
        init();
    }
    
    public ActividadModalDlg(long id){
        init();
        loadData(id);
    }
    
    private void init(){
        id = 0l;
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);

        nombre = new TextField();
            Element.cfgComponent(nombre,"Nombre");
            nombre.setPlaceholder("Ingresa el nombre de la actividad sin acentos");
        tituloPuntaje = new Label("Puntaje");
            Element.cfgComponent(tituloPuntaje);
            tituloPuntaje.addStyleNames(ValoTheme.LABEL_SMALL);
        puntaje = new Slider(1, 1000);
            puntaje.setOrientation(SliderOrientation.HORIZONTAL);
            puntaje.setWidth("100%");
            puntaje.isResponsive(); 
        fileUploadReceiver = new FileUploadReceiver();
        fileUpload = new Upload();
        Element.cfgComponent(fileUpload, "Subir datos de Actividades");
        fileUpload.setReceiver(fileUploadReceiver);
        fileUpload.addFinishedListener(listener -> {
            Element.makeNotification("El archivo a subido correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
            fileUpload.setCaption(fileUploadReceiver.getFile().getName());
        });
        
        agregar = new Button("Agregar");
            Element.cfgComponent(agregar);
            agregar.setStyleName(ValoTheme.BUTTON_DANGER);
            agregar.addClickListener((Button.ClickEvent event) -> { buttonAgregarEvent();});
 
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,6,8,9).withComponent(fileUpload);
            row1.addColumn().withDisplayRules(12,6,4,3).withComponent(agregar);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(tituloPuntaje);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(puntaje);       
        contentLayout.addComponent(content);
        setCaption("Actividades");
        setWidth("50%");
        
    }

    @Override
    protected void loadData(Long id){
        try{
            Actividad obj = ui.getControladorActividad().getRepository().findById(id).get();
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());
            puntaje.setValue(obj.getPuntaje());
            unidad=obj.getObjUnidad();
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }
    
    @Override
    protected void buttonAcceptEvent(){
        try{
            Actividad obj = new Actividad();
                obj.setId(id);
                obj.setNombre(nombre.getValue());
                obj.setPuntaje(puntaje.getValue().intValue());
                obj.setUnidad(unidad.getId());
            obj = ui.getControladorActividad().guardar(obj);
            if(obj != null){
                Element.makeNotification("Datos guardados correctamente",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                if(actividadDlg != null) actividadDlg.updateDlg();
                close();
            }
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
               
    private void buttonAgregarEvent(){
        try{
            ui.getControladorActividad().importarDesdeCSV(unidad ,fileUploadReceiver.getFile().getAbsolutePath(),",");
            Element.makeNotification("Datos actualizados",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
            loadData(id);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(),Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
        
    @Override protected void buttonCancelEvent(){close();}
    @Override protected void buttonDeleteEvent(){}

}
