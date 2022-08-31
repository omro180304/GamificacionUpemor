package com.upemor.gamificacion.vista.administracion.materia;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MateriaModalDlg extends TemplateModalWin<Long>{
    
    private TextField nombre;
    
    public MateriaModalDlg(){
        id = 0l;
        init();
    }
    
    public MateriaModalDlg(Long id){
        init();
        loadData(id);
                
    }
    
    private void init(){
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        
        nombre = new TextField();
            Element.cfgComponent(nombre,"Nombre");
            nombre.setPlaceholder("Introduce el nombre de la materia, sin acentos ");
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(nombre);
        
        contentLayout.addComponent(content);
        setCaption("Materias");
        setWidth("50%");
    }

    @Override
    protected void loadData(Long id){
        try{
            Materia obj = ui.getControladorMateria().getRepository().findById(id).get();
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }

    @Override
    protected void buttonAcceptEvent(){
        try{
            Materia obj = new Materia();
                obj.setId(id);
                obj.setNombre(nombre.getValue());
            obj = ui.getControladorMateria().guardar(obj);
            if(obj != null){
                Element.makeNotification("Datos guardados correctamente",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getMateriaDlg().updateDlg();
                close();
            }
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(),Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }

    @Override
    protected void buttonCancelEvent(){
        close();
    }
    
    @Override protected void buttonDeleteEvent(){close();}
    
}
