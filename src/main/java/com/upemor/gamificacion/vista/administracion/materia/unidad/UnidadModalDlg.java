package com.upemor.gamificacion.vista.administracion.materia.unidad;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Unidad;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Setter;

public class UnidadModalDlg extends TemplateModalWin<Long>{
    
    @Setter private UnidadDlg unidadDlg;
    @Setter private Materia materia;
    
    private TextField nombre;
    
    public UnidadModalDlg(){
        init();
        loadData(id);
    }
    
    public UnidadModalDlg(long id){
        init();
        loadData(id);
    }
    
    private void init(){
        id = 0l;
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);

        nombre = new TextField();
            Element.cfgComponent(nombre,"Nombre");
            nombre.setPlaceholder("Introduce el nombre de la unidad, sin acentos ");
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1, true, false);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);

        contentLayout.addComponent(content);
        setCaption("Unidades");
        setWidth("50%");
    }

    @Override
    protected void loadData(Long id){
        try{
            Unidad obj = ui.getControladorUnidad().getRepository().findById(id).get();
            this.id = obj.getId();
            materia = obj.getObjMateria();
            nombre.setValue(obj.getNombre());
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }
    
    @Override
    protected void buttonAcceptEvent(){
        try{
            Unidad obj = new Unidad();
                obj.setId(id);
                obj.setMateria(materia.getId());
                obj.setNombre(nombre.getValue());
            obj = ui.getControladorUnidad().guardar(obj);
            if(obj != null){
                Element.makeNotification("Datos guardados correctamente",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                if(unidadDlg != null) unidadDlg.updateDlg();
                close();
            }
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }

    @Override protected void buttonCancelEvent(){close();}
    @Override protected void buttonDeleteEvent(){}}
