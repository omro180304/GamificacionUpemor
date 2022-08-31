package com.upemor.gamificacion.vista.administracion.materia.unidad;

import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Unidad;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.utils.TemplateDlgWin;
import com.upemor.gamificacion.vista.administracion.materia.unidad.actividad.ActividadDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;

public class UnidadDlg extends TemplateDlgWin<Unidad>{
    
    private Materia materia; 
    
    public UnidadDlg(Materia materia){
        this.materia = materia;
        init();
    }
    
    public void init(){
        
        searchField.setPlaceholder("Proporcione el nombre a buscar...");
        
        grid.addComponentColumn(this::buildEliminarButton).setCaption("");
        grid.addColumn(Unidad::getId).setCaption("Id");
        grid.addColumn(Unidad::getNombre).setCaption("Nombre");
        grid.addComponentColumn(this::buildActividadesButton).setCaption("Actividades");

        setCaption("Unidades");
        buttonSearchEvent();
    }
    
    private Button buildActividadesButton(Unidad obj){
        Button button = new Button(obj.getListActividades().size()+" Actividades",VaadinIcons.PENCIL);
            button.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
            button.setDescription("Editar Actividades");
            button.addClickListener(e -> {
                ui.addWindow(new ActividadDlg(obj));
            });
        return button;
    }
    
    private Button buildEliminarButton(Unidad obj){
        Button button = new Button("",VaadinIcons.MINUS_CIRCLE);
            button.addStyleNames(ValoTheme.BUTTON_BORDERLESS,ValoTheme.BUTTON_ICON_ONLY);
            button.setDescription("Eliminar unidad");
            button.addClickListener(e -> {
                ui.getControladorUnidad().getRepository().delete(obj);
                updateDlg();
            });
        return button;
    }

    @Override
    protected void buttonSearchEvent(){
        List<Unidad> unidades = ui.getControladorMateria().getRepository().findById(materia.getId()).get().getListUnidades();
        grid.setItems(unidades);
    }

    @Override
    protected void buttonAddEvent(){
        UnidadModalDlg win = new UnidadModalDlg();
            win.setMateria(materia);
            win.setUnidadDlg(this);
        ui.addWindow(win);
    }

    @Override
    protected void eventEditButtonGrid(Unidad obj){
        UnidadModalDlg win = new UnidadModalDlg(obj.getId());
            win.setUnidadDlg(this);
        ui.addWindow(win);
    }
    
}
