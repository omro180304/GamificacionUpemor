package com.upemor.gamificacion.vista.principal.alumnoLogin;

import com.upemor.gamificacion.controladores.ControladorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Premio;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.upemor.gamificacion.persistencia.entidad.Canje;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.TemplateDlg;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.themes.ValoTheme;
import com.upemor.gamificacion.MainUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import com.vaadin.ui.Alignment;
import java.util.logging.Level;
import java.text.NumberFormat;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Image;

public class AlumnoVista extends TemplateDlg<Alumno>{
    //variables
    private MainUI ui;
    private MenuBar menuBar;
    private ComboBox<Grupo> grupo;
    private ComboBox<Materia> materia;
    private ComboBox<Usuario> profesor;
    private ResponsiveLayout contenido;
    private ResponsiveRow row2;
    //constructor
    public AlumnoVista(){
        init();
    }
    
    public void init(){
        ui = Element.getUI();       
        
        Image logo = new Image("", new ThemeResource("images/LogoUpe.png"));
            Element.cfgComponent(logo);
            logo.setWidth("20%");
            

        /* Creando menu de opciones del alumno */
        menuBar = new MenuBar();
            menuBar.setWidth("100%");
            menuBar.setResponsive(true);
            MenuBar.MenuItem alumno = menuBar.addItem(ui.getAlumno().getCorreo(),VaadinIcons.USER,null);
                MenuBar.MenuItem cerrarSesion = alumno.addItem("Salir",VaadinIcons.SIGN_OUT,cmd -> {logoutEvent();});
                MenuBar.MenuItem cambioContrasena = alumno.addItem("Cambiar Contraseña",VaadinIcons.EDIT,cmd -> {cambioContrasenaBtt(ui.getAlumno());});
            MenuBar.MenuItem saldo = menuBar.addItem(Double.toString(ui.getAlumno().getSaldo()), VaadinIcons.COIN_PILES, null);
        
        //Combo box donde se visualizan los grupos a los que pertenece el alumno sesion
        grupo = new ComboBox<>();
            Element.cfgComponent(grupo,"Grupo");
            grupo.setItems(ui.getAlumno().getListaGrupos());
            grupo.addValueChangeListener(event -> {
                Grupo objGrupo = grupo.getValue();
                if(objGrupo == null){return;}
                materia.setItems(objGrupo.getListaMaterias());
            });
        //Combo box donde se visualizan las materias a los que pertenece el alumno en sesion            
        materia = new ComboBox<>();
            Element.cfgComponent(materia, "Materia");
            materia.addValueChangeListener(event -> {eventoComboBoxMateria();});
 
        // creacion del contenedor y colocacion de los elementos
        contenido = new ResponsiveLayout();
            Element.cfgLayoutComponent(contenido);
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row1,true,true);
            row1.addColumn().withDisplayRules(12,12,6,6).withComponent(grupo);
            row1.addColumn().withDisplayRules(12,12,6,6).withComponent(materia);
        row2 = contenido.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,true);
            
        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.setSpacing(false);
            main.addComponent(menuBar);
            main.addComponent(contenido);
            
        setContent(main);
        setSizeFull();     
        eventoComboBoxMateria();
       
    }
    
    protected void cambioContrasenaBtt(Alumno alumno) {
        ui.addWindow(new AlumnoCambioContrasenaModalDlg(ui.getAlumno().getId()));   
    }
    
    private void actualizarContenido(Panel panel){
        contenido.removeAllComponents();
        contenido.addComponent(panel);
    }
    
    private void logoutEvent(){
        this.getUI().getSession().close();
        this.getUI().getPage().reload();
    }
    
    private void eventoComboBoxMateria(){
        try{
            row2.removeAllComponents();
            Materia objMateria = materia.getValue();
            
            if(objMateria == null){return;}
            for(Premio premio:objMateria.getListPremios()){
                row2.addColumn().withDisplayRules(12,6,4,3).withComponent(new PremioDlg(premio));
            }
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
    }
    
    protected void buttonSearchEvent(){}
    protected void buttonAddEvent() {}
    protected void eventEditButtonGrid(Alumno obj) { }
}

// clase que carga la informacion de los premios de tienda
class PremioDlg extends Panel{
    @Autowired private ControladorUsuario controladorUsuario;
    private VerticalLayout main;
    private MainUI ui;
    private Premio premio;
    private Usuario user;
    private Image image;
    private Label nombre;
    private Label precio;
    private Label stock;
    private Button canjear;

    public PremioDlg(Premio premio){
        this.premio = premio;
        init();
        loadData();
    }

    private void init(){
        ui = Element.getUI();
        
        nombre = new Label();
            Element.cfgComponent(nombre,"Nombre");
            nombre.addStyleNames(ValoTheme.LABEL_LARGE,ValoTheme.LABEL_BOLD);
        precio = new Label();
            Element.cfgComponent(precio,"Precio");
        stock = new Label();
            Element.cfgComponent(stock,"Stock");
            stock.addStyleNames(ValoTheme.LABEL_TINY,ValoTheme.LABEL_BOLD);
        image = new Image();
            image.setWidth("100%");
        //boton de canje    
        canjear = new Button("", VaadinIcons.DOLLAR);
            Element.cfgComponent(canjear);
            canjear.setStyleName(ValoTheme.BUTTON_FRIENDLY);
            canjear.addClickListener((Button.ClickEvent event) -> {eventButtonCanjear();});
            canjear.setSizeFull();
        
        ResponsiveLayout datosPremio = new ResponsiveLayout();
            Element.cfgLayoutComponent(datosPremio);
        ResponsiveRow datosPremioRow1 = datosPremio.addRow().withAlignment(Alignment.TOP_CENTER);
            Element.cfgLayoutComponent(datosPremioRow1,true,true);
            datosPremioRow1.addColumn().withDisplayRules(12,12,12,12).withComponent(nombre);
            datosPremioRow1.addColumn().withDisplayRules(12,12,12,12).withComponent(precio);
            datosPremioRow1.addColumn().withDisplayRules(12,12,12,12).withComponent(stock);
            datosPremioRow1.addColumn().withDisplayRules(12,12,12,12).withComponent(canjear);
            
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
            content.setSizeFull();
        ResponsiveRow contentRow1 = content.addRow().withAlignment(Alignment.TOP_CENTER);
            Element.cfgLayoutComponent(contentRow1,true,true);
            contentRow1.addColumn().withDisplayRules(12,5,4,3).withComponent(image);
            contentRow1.addColumn().withDisplayRules(12,7,8,9).withComponent(datosPremio);
        
        main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(content);
            main.setSizeFull();
        
        this.setSizeFull();
        this.setContent(main);
        this.setCaptionAsHtml(true);
        this.addStyleName("panel-friendly");
        loadData();
    }
    
    public void loadData(){//carga la informacion de los premios
        image.setSource(new ExternalResource(premio.getImagenLink()));
        nombre.setValue(premio.getNombre());
        precio.setValue(NumberFormat.getCurrencyInstance().format(premio.getPrecio()));
        stock.setValue(NumberFormat.getIntegerInstance().format(premio.getStock()));
    }

    private void eventButtonCanjear() {//logica del boton canjear
        try {
            Canje canje = new Canje();
                canje.setAlumno(ui.getAlumno().getId());
                canje.setPremio(premio.getId());
                canje.setFecha(LocalDateTime.now());
                canje.setPrecio((long) premio.getPrecio());
            canje = ui.getControladorCanje().guardar(canje);
            if (canje != null) {
                Element.makeNotification("Canje completado correctamente", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                loadData();
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    
}