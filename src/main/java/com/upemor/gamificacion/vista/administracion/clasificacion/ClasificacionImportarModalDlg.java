package com.upemor.gamificacion.vista.administracion.clasificacion;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.utils.FileUploadReceiver;
import com.vaadin.shared.Position;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import java.util.logging.Level;

public class ClasificacionImportarModalDlg extends TemplateModalWin<Long>{
    
    private ComboBox<Materia> materia;
    private FileUploadReceiver fileUploadReceiver;
    private Upload fileUpload;
    
    public ClasificacionImportarModalDlg(){
        id = 0l;
        init();
    }
    
    private void init(){
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        
            materia = new ComboBox<>();
                Element.cfgComponent(materia,"Materia");
                materia.setItems(ui.getControladorMateria().getRepository().getByNombreLikeOrderByNombre("%"));
            fileUploadReceiver = new FileUploadReceiver();
            fileUpload = new Upload();
                Element.cfgComponent(fileUpload, "Subir datos...");
                fileUpload.setReceiver(fileUploadReceiver);
                fileUpload.addFinishedListener(listener -> {
                    Element.makeNotification("El archivo a subido correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                    fileUpload.setCaption(fileUploadReceiver.getFile().getName());
                });
            
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(materia);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(fileUpload);
            
        contentLayout.addComponent(content);
        setCaption("Tabla Clasificacion");
        setWidth("50%");
    }
    

    
    @Override
    protected void buttonAcceptEvent(){
        try{
            Materia objMateria = materia.getValue();
            ui.getControladorClasificacion().importarDesdeCSV(fileUploadReceiver.getFile().getAbsolutePath(),",",objMateria);
            Element.makeNotification("Datos guardados correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    @Override protected void loadData(Long id){}
    @Override protected void buttonCancelEvent(){close();}
    @Override protected void buttonDeleteEvent(){}

}