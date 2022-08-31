package com.upemor.gamificacion.vista.administracion.materia.premio;

import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Premio;
import com.upemor.gamificacion.utils.TemplateDlgWin;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import java.util.List;

public class PremioDlg extends TemplateDlgWin<Premio>{
    
    private Materia materia;
    
    public PremioDlg(Materia materia){
        this.materia = materia;
        init();
    }
    
    public void init(){
        searchField.setPlaceholder("Proporcione el nombre a buscar...");
        
        grid.addComponentColumn(this::buildEliminarButton).setCaption("");
        grid.addColumn(Premio::getId).setCaption("Id");
        grid.addColumn(Premio::getNombre).setCaption("Nombre");
        grid.addColumn(Premio::getPrecio).setCaption("Precio");
        grid.addColumn(Premio::getStock).setCaption("Stock");
        grid.addColumn(Premio::getImagenLink).setCaption("Direccion de la imagen");

        setCaption("Premios");
        buttonSearchEvent();
    }
    
    private Button buildEliminarButton(Premio obj){
        Button button = new Button("",VaadinIcons.MINUS_CIRCLE);
            button.addStyleNames(ValoTheme.BUTTON_BORDERLESS,ValoTheme.BUTTON_ICON_ONLY);
            button.setDescription("Eliminar Premio");
            button.addClickListener(e -> {
                ui.getControladorPremio().getRepository().delete(obj);
                updateDlg();
            });
        return button;
    }

    @Override
    protected void buttonSearchEvent(){
        List<Premio> premios = ui.getControladorMateria().getRepository().findById(materia.getId()).get().getListPremios();
        grid.setItems(premios);
    }

    @Override
    protected void buttonAddEvent(){
        PremioModalDlg win = new PremioModalDlg();
            win.setMateria(materia);
            win.setPremioDlg(this);
        ui.addWindow(win);
    }

    @Override
    protected void eventEditButtonGrid(Premio obj){
        PremioModalDlg win = new PremioModalDlg(obj.getId());
            win.setPremioDlg(this);
        ui.addWindow(win);
    }
    
}
