package com.upemor.gamificacion.vista.administracion.usuario;

import com.upemor.gamificacion.persistencia.entidad.UsuarioTipo;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.Notification;
import com.vaadin.shared.Position;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TextField;
import java.util.logging.Level;
import com.vaadin.ui.ComboBox;

public class UsuarioModalDlg extends TemplateModalWin<Long>{
    
    private TextField nombres;
    private TextField apellidos;
    private TextField correo;
    private TextField palabraClave;
    private ComboBox<UsuarioTipo> usuarioTipo;
    
    public UsuarioModalDlg(){
        id = 0l;
        init();
    }
    
    public UsuarioModalDlg(Long id){
        init();
        loadData(id);
                
    }
    
    private void init(){
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
        
        nombres = new TextField();
            Element.cfgComponent(nombres,"Nombres");
            nombres.setPlaceholder("Escribe tu nombre(s)");
            
        apellidos = new TextField();
            Element.cfgComponent(apellidos,"Apellidos");
            apellidos.setPlaceholder("Introduce tu(s) apellido(s), sin acentos ");
            
        correo = new TextField();
            Element.cfgComponent(correo,"Correo");
            correo.setPlaceholder("Introduce tu correo upemor");
            
        palabraClave = new TextField();
            Element.cfgComponent(palabraClave,"Palabra clave");
            palabraClave.setPlaceholder("Introduce tu palabra clave de recuperacion");
            palabraClave.setMaxLength(15);
            
        usuarioTipo = new ComboBox<>();
            Element.cfgComponent(usuarioTipo,"UsuarioTipo");
            usuarioTipo.setPlaceholder("Selecciona el tipo de usuario");
            usuarioTipo.setItems(ui.getControladorUsuarioTipo().getRepository().getByNombreLikeOrderByNombre("%"));
           
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(nombres);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(apellidos);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(correo);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(palabraClave);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(usuarioTipo);
        
        contentLayout.addComponent(content);
        setCaption("Usuarios");
        setWidth("50%");
    }

    @Override
    protected void loadData(Long id){
        try{
            Usuario obj = ui.getControladorUsuario().getRepository().findById(id).get();
            this.id = obj.getId();
            nombres.setValue(obj.getNombres());
            apellidos.setValue(obj.getApellidos());
            correo.setValue(obj.getCorreo());
            palabraClave.setValue(obj.getPalabraClave());
            usuarioTipo.setValue(obj.getObjUsuarioTipo());
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }
    
    @Override
    protected void buttonAcceptEvent(){
        try{
            Usuario obj = new Usuario();
                obj.setId(id);
                obj.setNombres(nombres.getValue());
                obj.setApellidos(apellidos.getValue());
                obj.setUsuarioTipo(usuarioTipo.getValue()==null?0:usuarioTipo.getValue().getId());
                obj.setPalabraClave(palabraClave.getValue());
                obj.setCorreo(correo.getValue());
            obj = ui.getControladorUsuario().guardar(obj);
            if(obj != null){
                Element.makeNotification("Datos guardados correctamente",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getUsuarioDlg().updateDlg();
                close();
            }else{
                Element.makeNotification("Error en los datos ingresados",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getUsuarioDlg();
                
            }
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(),Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }

    @Override protected void buttonCancelEvent(){close();}
    
    @Override    protected void buttonDeleteEvent(){close();}

}
