package com.upemor.gamificacion.vista.administracion.grupo.grupomateriaprofesor;

import com.upemor.gamificacion.persistencia.entidad.GrupoMateriaProfesor;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import java.util.logging.Level;
import com.vaadin.ui.ComboBox;
import lombok.Setter;

public class GrupoMateriaProfesorWinDlg extends TemplateModalWin<Long>{
    
    @Setter private GrupoMateriaProfesorDlg grupoMateriaProfesorDlg;
    private Grupo grupo;
    
    private ComboBox<Materia> materia;
    private ComboBox<Usuario> profesor;
    
    public GrupoMateriaProfesorWinDlg(Grupo grupo){
        id = 0l;
        this.grupo = grupo;
        init();
    }
    
    public GrupoMateriaProfesorWinDlg(Long id){
        init();   
    }
    
    private void init(){
        id = 0l;
        
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        materia = new ComboBox<>();
            Element.cfgComponent(materia,"Materia");
            materia.setItems(ui.getControladorMateria().getRepository().getByNombreLikeOrderByNombre("%"));
            materia.setPlaceholder("Selecciona una materia");
        profesor = new ComboBox<>();
            Element.cfgComponent(profesor,"Profesor");
            profesor.setPlaceholder("Selecciona el profesor que impartira");
            profesor.setItems(ui.getControladorUsuario().getRepository().getByNombresLikeOrderByNombres("%"));   
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(materia);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(profesor);   
        
        contentLayout.addComponent(content);
        setCaption("Agregar Materia y Profesor");
        setWidth("50%");
    }
    
    @Override
    protected void loadData(Long id){
        try{
            GrupoMateriaProfesor obj = ui.getControladorGrupoMateriaProfesor().getRepository().getOne(id);
            this.id = obj.getId();
            profesor.setValue(obj.getObjProfesor());
            materia.setValue(obj.getObjMateria());
            grupo = obj.getObjGrupo();
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(),Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    @Override
    protected void buttonAcceptEvent(){
        try{
            GrupoMateriaProfesor obj = new GrupoMateriaProfesor();
                obj.setMateria(materia.getValue()==null?0:materia.getValue().getId());
                obj.setProfesor(profesor.getValue()==null?0:profesor.getValue().getId());
                obj.setGrupo(grupo.getId());
            obj = ui.getControladorGrupoMateriaProfesor().guardar(obj);
            if(obj != null){
                Element.makeNotification("Datos guardados correctamente",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getGrupoDlg().updateDlg();
                if(grupoMateriaProfesorDlg != null){grupoMateriaProfesorDlg.updateDlg();}
                close();
            }
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(),Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }

    @Override
    protected void buttonCancelEvent(){close();}

    @Override protected void buttonDeleteEvent(){}
}