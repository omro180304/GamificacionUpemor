package com.upemor.gamificacion.vista.administracion.materia.unidad.actividad;

import com.upemor.gamificacion.persistencia.entidad.Actividad;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Unidad;
import com.upemor.gamificacion.utils.TemplateDlgWin;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.icons.VaadinIcons;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.vaadin.ui.Button;
import java.util.List;

public class ActividadDlg extends TemplateDlgWin<Actividad>{
       
    private Materia materia;
    private Unidad unidad;
    
    public ActividadDlg(Unidad unidad){
        this.unidad = unidad;
        init();
    }
    
    public void init(){
        searchField.setPlaceholder("Proporcione el nombre a buscar...");
        
        grid.addComponentColumn(this::buildEliminarButton).setCaption("");
        grid.addColumn(Actividad::getId).setCaption("Id");
        grid.addColumn(Actividad::getNombre).setCaption("Nombre");
        grid.addColumn(Actividad::getPuntaje).setCaption("Puntaje");
        
       setCaption("Actividades");
       buttonSearchEvent();
    }
    
    private Button buildEliminarButton(Actividad obj){
        Button button = new Button("",VaadinIcons.MINUS_CIRCLE);
            button.addStyleNames(ValoTheme.BUTTON_BORDERLESS,ValoTheme.BUTTON_ICON_ONLY);
            button.setDescription("Eliminar actividad");
            button.addClickListener(e -> {
                ui.getControladorActividad().getRepository().delete(obj);
                updateDlg();
            });
        return button;
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            List<Actividad> actividades = ui.getControladorActividad().getRepository().getByUnidadAndNombreLikeOrderByNombre(unidad.getId(),searchField.getValue()+"%");
            grid.setItems(actividades);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        ActividadModalDlg win = new ActividadModalDlg();
            win.setUnidad(unidad);
            win.setActividadDlg(this);
        ui.addWindow(win);
    }

    @Override
    protected void eventEditButtonGrid(Actividad obj){
        ActividadModalDlg win = new ActividadModalDlg(obj.getId());
            win.setActividadDlg(this);
        ui.addWindow(win);
    }
    
}