package com.upemor.gamificacion.vista.administracion.materia;

import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.utils.TemplateDlg;
import com.upemor.gamificacion.vista.administracion.materia.premio.PremioDlg;
import com.upemor.gamificacion.vista.administracion.materia.unidad.UnidadDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MateriaDlg extends TemplateDlg<Materia>{

    public MateriaDlg(){
        init();
    }

    private void init(){
        //Se inicializan y muestran datos de forma tabular.
        searchField.setPlaceholder("Proporcione el nombre a buscar...");
        grid.addComponentColumn(this::buildEliminarButton).setCaption("");
        grid.addColumn(Materia::getId).setCaption("Id");
        grid.addColumn(Materia::getNombre).setCaption("Nombre");
        grid.addComponentColumn(this::buildUnidadesButton).setCaption("Unidades");
        grid.addComponentColumn(this::buildPremiosButton).setCaption("Premios");

        setCaption("Materias");//titulo del modulo
    }
    //botones que crean una ventana emergente
    private Button buildUnidadesButton(Materia obj){
        Button button = new Button(obj.getListUnidades().size()+" Unidades",VaadinIcons.FOLDER);
            button.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
            button.setDescription("Unidades");
            button.addClickListener(e -> {
                ui.addWindow(new UnidadDlg(obj));
            });
        return button;
    } 
    private Button buildPremiosButton(Materia obj){
        Button button = new Button(obj.getListPremios().size()+" Premios",VaadinIcons.GIFT);
            button.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
            button.setDescription("Premios");
            button.addClickListener(e -> {
                ui.addWindow(new PremioDlg(obj));
            });
        return button;
    }
    
    @Override protected void buttonSearchEvent() {//boton buscador de materias/actualiza
        try {
            List<Materia> lista = ui.getControladorMateria().getRepository().getByNombreLikeOrderByNombre(searchField.getValue() + "%");
            grid.setItems(lista);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
    }

    //boton de operaciones de materias
    @Override protected void buttonAddEvent() {
        ui.addWindow(new MateriaModalDlg());
    }

    @Override protected void eventEditButtonGrid(Materia obj) {
        ui.addWindow(new MateriaModalDlg(obj.getId()));
    }

    private Button buildEliminarButton(Materia obj) {
        Button button = new Button("", VaadinIcons.MINUS_CIRCLE);
        button.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Eliminar Materia");
        button.addClickListener(e -> {
            ui.getControladorMateria().getRepository().delete(obj);
            updateDlg();
        });
        return button;
    }
}
