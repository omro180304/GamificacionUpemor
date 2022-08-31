package com.upemor.gamificacion.vista.administracion.grupo.grupomateriaprofesor;

import com.upemor.gamificacion.persistencia.entidad.GrupoMateriaProfesor;
import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.upemor.gamificacion.utils.TemplateDlgWin;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Setter;

public class GrupoMateriaProfesorDlg extends TemplateDlgWin<GrupoMateriaProfesor>{
    
    @Setter private Grupo grupo;
    
    public GrupoMateriaProfesorDlg(Grupo grupo){
        this.grupo = grupo;
        init();
    }
    
    private void init(){
        searchField.setPlaceholder("Proporcione el nombre de la materia");
        grid.addColumn(GrupoMateriaProfesor::getObjProfesor).setCaption("Profesor");
        grid.addColumn(GrupoMateriaProfesor::getObjMateria).setCaption("Materia");
        
        this.setCaption("Materia y Profesor");
        updateDlg();
    }

    @Override protected void buttonSearchEvent(){
        try{
            List<GrupoMateriaProfesor> lista = ui.getControladorGrupoMateriaProfesor().getRepository().getByGrupoAndObjMateria_nombreLikeOrderByObjMateria_nombre(grupo.getId(),searchField.getValue()+"%");
            grid.setItems(lista);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
    }
    
    @Override protected void buttonAddEvent(){
        GrupoMateriaProfesorWinDlg win = new GrupoMateriaProfesorWinDlg(grupo);
            win.setGrupoMateriaProfesorDlg(this);
        ui.addWindow(win);
    }
    
    @Override protected void eventEditButtonGrid(GrupoMateriaProfesor obj){
        GrupoMateriaProfesorWinDlg win = new GrupoMateriaProfesorWinDlg(obj.getId());
            win.setGrupoMateriaProfesorDlg(this);
        ui.addWindow(win);
    }
}
