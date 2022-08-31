package com.upemor.gamificacion.vista.administracion.alumno;

import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.utils.FileUploadReceiver;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.TemplateDlg;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Upload;
import java.util.List;

public class AlumnoDlg extends TemplateDlg<Alumno>{
    
    private FileUploadReceiver fileUploadReceiver;
    private Upload fileUpload;
    private Button agregar;
    
    public AlumnoDlg(){
        init();
    }
    
    private void init(){
        
        searchField.setPlaceholder("Proporcione el nombre a buscar...");
        grid.addComponentColumn(this::buildEliminarButton).setCaption("");
        grid.addColumn(Alumno::getId).setCaption("Id");
        grid.addColumn(Alumno::getNombres).setCaption("nombres");
        grid.addColumn(Alumno::getApellidos).setCaption("Apellidos");
        grid.addColumn(Alumno::getCorreo).setCaption("Correo");
        grid.addColumn(Alumno::getMatricula).setCaption("Matricula");
        grid.addColumn(Alumno::getPalabraClave).setCaption("PalabraClave");
        grid.addColumn(Alumno::getSaldo).setCaption("Saldo");
        
        //subida del archivo excel
        fileUploadReceiver = new FileUploadReceiver();
        fileUpload = new Upload();
        Element.cfgComponent(fileUpload, "Subir datos de los alumnos");
        fileUpload.setReceiver(fileUploadReceiver);
        fileUpload.addFinishedListener(listener -> {
            Element.makeNotification("El archivo a subido correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
            fileUpload.setCaption(fileUploadReceiver.getFile().getName());
        });
        agregar = new Button("Agregar");
            Element.cfgComponent(agregar);
            agregar.setStyleName(ValoTheme.BUTTON_DANGER);
            agregar.addClickListener((Button.ClickEvent event) -> { buttonAgregarEvent();});
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,6,8,9).withComponent(fileUpload);
            row1.addColumn().withDisplayRules(12,6,4,3).withComponent(agregar);
        
        contentLayout.addComponent(content);
        setCaption ("Alumnos");
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            List<Alumno> lista = ui.getControladorAlumno().getRepository().getByNombresLikeOrderByNombres(searchField.getValue()+"%");
            grid.setItems(lista);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new AlumnoModalDlg());
    }

    @Override
    protected void eventEditButtonGrid(Alumno obj){
        ui.addWindow(new AlumnoModalDlg(obj.getId()));
    }
    
    private void buttonAgregarEvent(){
        try{
            ui.getControladorAlumno().importarDesdeCSV(fileUploadReceiver.getFile().getAbsolutePath(),",");
            Element.makeNotification("Datos actualizados",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
            updateDlg();
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(),Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    private Button buildEliminarButton(Alumno obj) {
        Button button = new Button("", VaadinIcons.MINUS_CIRCLE);
        button.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Eliminar Alumno");
        button.addClickListener(e -> {
            ui.getControladorAlumno().getRepository().delete(obj);
            updateDlg();
        });
        return button;
    }
}
