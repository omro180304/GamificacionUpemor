package com.upemor.gamificacion.vista.administracion.grupo.grupoalumno;

import com.upemor.gamificacion.vista.administracion.grupo.GrupoDlg;
import com.upemor.gamificacion.persistencia.entidad.GrupoAlumno;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.upemor.gamificacion.utils.FileUploadReceiver;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.themes.ValoTheme;
import com.upemor.gamificacion.MainUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import com.vaadin.ui.Grid;
import java.util.List;
import lombok.Setter;

public class GrupoAlumnoModalDlg extends Window{
    
    private MainUI ui;
    @Setter private GrupoDlg grupoDlg;
    private Grupo grupo;
    
    private Grid<Alumno> alumnos;
    
    private FileUploadReceiver fileUploadReceiver;
    private Upload fileUpload;
    private Button accept;
    private Button cancel;
    
    public GrupoAlumnoModalDlg(Grupo grupo){
        this.grupo = grupo;
        init();
        loadData();
    }
    
    private void init(){
        ui = Element.getUI();
        
        alumnos = new Grid<>();
            Element.cfgComponent(alumnos,"Alumnos");
            alumnos.addColumn(Alumno::getMatricula).setCaption("Matricula");
            alumnos.addColumn(Alumno::getNombres).setCaption("Nombres");
            alumnos.addColumn(Alumno::getApellidos).setCaption("Apellidos");
            alumnos.addColumn(Alumno::getCorreo).setCaption("Correo");
        alumnos.getDataProvider().refreshAll();
            
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
            ResponsiveRow contentRow1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
                Element.cfgLayoutComponent(contentRow1,true,true);
                contentRow1.addColumn().withDisplayRules(12,12,12,12).withComponent(alumnos);
        
        fileUploadReceiver = new FileUploadReceiver();
        fileUpload = new Upload();
        Element.cfgComponent(fileUpload,"Subir datos de los alumnos");
        fileUpload.setReceiver(fileUploadReceiver);
        fileUpload.addFinishedListener(listener -> {
            Element.makeNotification("El archivo a subido correctamente",Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
            fileUpload.setCaption(fileUploadReceiver.getFile().getName());
        });
        accept = new Button("Guardar");
            Element.cfgComponent(accept);
            accept.addClickListener((Button.ClickEvent event) -> {buttonAcceptEvent();});
            accept.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel = new Button("Cancelar");
            Element.cfgComponent(cancel);
            cancel.addClickListener((Button.ClickEvent event) -> {buttonCancelEvent();});
            cancel.setStyleName(ValoTheme.BUTTON_DANGER);
        ResponsiveLayout actions = new ResponsiveLayout();
            Element.cfgLayoutComponent(actions);
            ResponsiveRow actionsRow1 = actions.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
                Element.cfgLayoutComponent(actionsRow1,true,true);
                actionsRow1.addColumn().withDisplayRules(12,4,6,6).withComponent(fileUpload);
                actionsRow1.addColumn().withDisplayRules(12,4,3,3).withComponent(accept);
                actionsRow1.addColumn().withDisplayRules(12,4,3,3).withComponent(cancel);
        
        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(content);
            main.addComponent(actions);
            main.setComponentAlignment(content,Alignment.MIDDLE_CENTER);
            main.setComponentAlignment(actions,Alignment.MIDDLE_CENTER);
        
        this.setContent(main);
        this.setModal(true);
        this.setClosable(true);
        this.setResizable(false);
        this.setCaptionAsHtml(true);
        this.setWidth(Element.windowWidthPercent());
        this.setCaption("Alumnos del grupo");
    }

    private void loadData(){
        try{
            grupo = ui.getControladorGrupo().getRepository().findById(grupo.getId()).get();
            List<Alumno> listAlumnos = new ArrayList();
            for(GrupoAlumno grupoAlumno:grupo.getListGrupoAlumno()) listAlumnos.add(grupoAlumno.getObjAlumno());
            alumnos.setItems(listAlumnos);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }
    
    private void buttonAcceptEvent(){
        try{
            ui.getControladorGrupoAlumno().importarDesdeCSV(grupo,fileUploadReceiver.getFile().getAbsolutePath(),",");
            Element.makeNotification("Datos actualizados",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
            loadData();
            if(grupoDlg != null) grupoDlg.updateDlg();
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(),Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
            close();
        }
    }

    private void buttonCancelEvent(){
        close();
    }
    

}
