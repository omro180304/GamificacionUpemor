package com.upemor.gamificacion.vista.administracion.grupo;

import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.upemor.gamificacion.utils.TemplateDlg;
import com.upemor.gamificacion.vista.administracion.grupo.grupoalumno.GrupoAlumnoModalDlg;
import com.upemor.gamificacion.vista.administracion.grupo.grupomateriaprofesor.GrupoMateriaProfesorDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrupoDlg extends TemplateDlg<Grupo>{
    
    public GrupoDlg(){
        init();
    }
    
    private void init(){
        
        searchField.setPlaceholder("Proporcione el nombre a buscar...");
        grid.addComponentColumn(this::buildEliminarButton).setCaption("");
        grid.addColumn(Grupo::getId).setCaption("Id");
        grid.addColumn(Grupo::getNombre).setCaption("Nombre");
        grid.addColumn(Grupo::getGeneracion).setCaption("Generacion");
        grid.addComponentColumn(this::buildAlumnosButton).setCaption("Alumnos");
        grid.addComponentColumn(this::buildGrupoMateriaProfesorButton).setCaption("MateriaProfesor");
        
        setCaption ("Grupos");
    }
    
    private Button buildAlumnosButton(Grupo obj){
        Button button = new Button(obj.getListGrupoAlumno().size()+" alumno(s)",VaadinIcons.USER_CARD);
            button.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
            button.setDescription("Editar Grupo");
            button.addClickListener(e -> {
                GrupoAlumnoModalDlg win = new GrupoAlumnoModalDlg(obj);
                    win.setGrupoDlg(this);
                ui.addWindow(win);
            });
        return button;
    }
    
    private Button buildGrupoMateriaProfesorButton(Grupo obj){
        Button button = new Button(obj.getListGrupoMateriaProfesor().size()+" MateriaProfesor(s)",VaadinIcons.BRIEFCASE);
            button.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
            button.setDescription("Info Materia y Profesor");
            button.addClickListener(e -> {
                GrupoMateriaProfesorDlg win = new GrupoMateriaProfesorDlg(obj);
                ui.addWindow(win);
            });
        return button;
    }
        
    @Override
    protected void buttonSearchEvent(){
        try{
            List<Grupo> lista = ui.getControladorGrupo().getRepository().getByNombreLikeOrderByNombre(searchField.getValue()+"%");
            grid.setItems(lista);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new GrupoModalDlg());
    }

    @Override
    protected void eventEditButtonGrid(Grupo obj){
        ui.addWindow(new GrupoModalDlg(obj.getId()));
    }
    
    
    private Button buildEliminarButton(Grupo obj) {
        Button button = new Button("", VaadinIcons.MINUS_CIRCLE);
        button.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Eliminar Grupo");
        button.addClickListener(e -> {
            ui.getControladorGrupo().getRepository().delete(obj);
            updateDlg();
        });
        return button;
    }
}
