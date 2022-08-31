package com.upemor.gamificacion.vista.administracion.grupo;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.upemor.gamificacion.persistencia.entidad.Turno;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.utils.IntegerField;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrupoModalDlg extends TemplateModalWin<Long>{
    
    private TextField nombre;
    private IntegerField generacion;
    private ComboBox<Turno> turno;
    
    public GrupoModalDlg(){
        id = 0l;
        init();
    }
    
    public GrupoModalDlg(Long id){
        init();
        loadData(id);
                
    }
    
    private void init(){
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        
        nombre = new TextField();
            Element.cfgComponent(nombre,"Nombre");
            nombre.setPlaceholder("Introduce el grado y grupo, ej: 1A ");
        generacion = new IntegerField();
            Element.cfgComponent(generacion,"Generacion");
            generacion.setPlaceholder("Introduce la generacion del grupo, ej: 2018");
        turno = new ComboBox<>();
            Element.cfgComponent(turno, "Turno");
            turno.setPlaceholder("Selecciona una opcion de turno");
            turno.setItems(ui.getControladorTurno().getRepository().getByNombreLikeOrderByNombre("%"));
       
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(nombre);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(generacion);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(turno);
        
        contentLayout.addComponent(content);
        setCaption("Grupos");
        setWidth("50%");
    }

    @Override
    protected void loadData(Long id){
        try {
            Grupo obj = ui.getControladorGrupo().getRepository().findById(id).get();
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());
            generacion.setValue(obj.getGeneracion());
            turno.setValue(obj.getObjTurno()); 
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }

    @Override
    protected void buttonDeleteEvent(){}

    @Override
    protected void buttonCancelEvent(){
        close();
    }
    
    @Override
    protected void buttonAcceptEvent() {
        try {
            Grupo obj = new Grupo();
                obj.setId(id);
                obj.setNombre(nombre.getValue());
                obj.setGeneracion(generacion.getValue());
                obj.setTurno(turno.getValue() == null ? 0 : turno.getValue().getId());
            obj = ui.getControladorGrupo().guardar(obj);
            if (obj != null) {
                Element.makeNotification("Datos guardados correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getGrupoDlg().updateDlg();
                close();
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
}
