package com.upemor.gamificacion.vista.administracion.usuario;

import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.upemor.gamificacion.utils.TemplateDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDlg extends TemplateDlg<Usuario>{

    public UsuarioDlg(){
        init();
    }

    private void init(){
        
        searchField.setPlaceholder("Proporcione el nombre a buscar...");
        grid.addComponentColumn(this::buildEliminarButton).setCaption("");
        grid.addColumn(Usuario::getId).setCaption("Id");
        grid.addColumn(Usuario::getNombres).setCaption("nombres");
        grid.addColumn(Usuario::getApellidos).setCaption("Apellidos");
        grid.addColumn(Usuario::getCorreo).setCaption("Correo");
        grid.addColumn(Usuario::getPalabraClave).setCaption("PalabraClave");

        setCaption("Usuarios");
    }

    @Override
    protected void buttonSearchEvent() {
        try {
            List<Usuario> lista = ui.getControladorUsuario().getRepository().getByNombresLikeOrderByNombres(searchField.getValue() + "%");
            grid.setItems(lista);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new UsuarioModalDlg());
    }

    @Override
    protected void eventEditButtonGrid(Usuario obj) {
        ui.addWindow(new UsuarioModalDlg(obj.getId()));
    }
    
    private Button buildEliminarButton(Usuario obj) {
        Button button = new Button("", VaadinIcons.MINUS_CIRCLE);
        button.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Eliminar Usuario");
        button.addClickListener(e -> {
            ui.getControladorUsuario().getRepository().delete(obj);
            updateDlg();
        });
        return button;
    }

}
