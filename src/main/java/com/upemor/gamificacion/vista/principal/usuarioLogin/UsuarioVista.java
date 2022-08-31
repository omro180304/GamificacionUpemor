package com.upemor.gamificacion.vista.principal.usuarioLogin;

import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.TemplateDlg;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.MainUI;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Notification;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;

public class UsuarioVista extends TemplateDlg<Usuario>{
    private MainUI ui;
    private MenuBar menuBar;
    private ResponsiveLayout contenido;
    private Image icono;
    private Panel panel;
    
    public UsuarioVista(){
        init();
    }
    
    private void init(){
        ui = Element.getUI();
       
        /* Creando menu de opciones */
        menuBar = new MenuBar();
            menuBar.setWidth("100%");
            menuBar.setResponsive(true);          
            if (ui.getUsuario().getObjUsuarioTipo().isAdministrador()) {      
                MenuBar.MenuItem copiaSeguridad = menuBar.addItem("Crear copia de seguridad BD", VaadinIcons.DATABASE,cmd -> {crearCopiaBD();});                
                MenuBar.MenuItem usuarios = menuBar.addItem("USUARIOS", VaadinIcons.USERS,cmd -> {actualizarContenido(ui.getFabricaVista().getUsuarioDlg());});

            }
                MenuBar.MenuItem materias = menuBar.addItem("MATERIAS", VaadinIcons.BOOK, cmd -> {actualizarContenido(ui.getFabricaVista().getMateriaDlg());});
                MenuBar.MenuItem grupos = menuBar.addItem("GRUPOS", VaadinIcons.GROUP,cmd -> {actualizarContenido(ui.getFabricaVista().getGrupoDlg());});
                MenuBar.MenuItem alumnos = menuBar.addItem("ALUMNOS", VaadinIcons.USER,cmd -> {actualizarContenido(ui.getFabricaVista().getAlumnoDlg());});
                MenuBar.MenuItem clasificacion = menuBar.addItem("CLASIFICACIÓN", VaadinIcons.LIST,cmd -> {actualizarContenido(ui.getFabricaVista().getClasificacionDlgV2());});                
                MenuBar.MenuItem usuario = menuBar.addItem(ui.getUsuario().getCorreo(), VaadinIcons.USER, null);
                    MenuBar.MenuItem cerrarSesion = usuario.addItem("SALIR", VaadinIcons.SIGN_OUT, cmd -> {logoutEvent();});
                    MenuBar.MenuItem cambiarContrasena = usuario.addItem("CAMBIAR CONTRASEÑA", VaadinIcons.EDIT, cmd -> {cambioContrasenaBtt(ui.getUsuario());});
           
        contenido =new ResponsiveLayout();;
            Element.cfgLayoutComponent(contenido);
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.MIDDLE_CENTER);
            Element.cfgLayoutComponent(row1,true,true);
            
        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(menuBar);
            main.addComponent(contenido);
        
        setContent(main);
        setSizeFull();
    }
    
    private void actualizarContenido(Panel panel){
        contenido.removeAllComponents();
        contenido.addComponent(panel);
    }
    
    protected void cambioContrasenaBtt(Usuario usuario) {
        ui.addWindow(new UsuarioCambioContrasenaModalDlg(ui.getUsuario().getId()));
    }
    
    private void logoutEvent(){
        this.getUI().getSession().close();
        this.getUI().getPage().reload();
    }

    private void crearCopiaBD() {
        System.out.println("creando back up");
        MysqlExportDatabase obj = new MysqlExportDatabase();
        obj.createBackup();
        Element.makeNotification("Copia de seguridad creada", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());   
    }
    
    @Override protected void buttonSearchEvent() {}
    @Override protected void buttonAddEvent() {}
    @Override protected void eventEditButtonGrid(Usuario obj) {}
}
