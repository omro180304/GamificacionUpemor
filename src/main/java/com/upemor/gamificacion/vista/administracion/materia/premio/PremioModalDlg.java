package com.upemor.gamificacion.vista.administracion.materia.premio;

import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Premio;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.upemor.gamificacion.utils.TemplateModalWin;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import java.time.LocalDateTime;
import java.util.logging.Level;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Label;
import lombok.Setter;

public class PremioModalDlg extends TemplateModalWin<Long>{
    
    @Setter private PremioDlg premioDlg;
    @Setter private Materia materia;
    
    private TextField imagenLink;
    private TextField nombre;
    private Label tituloStock;
    private Label tituloPrecio;
    private Slider precio;
    private Slider stock;
    private ComboBox<Usuario> profesor;
    
    public PremioModalDlg(){
        init();
    }
    
    public PremioModalDlg(long id){
        init();
        loadData(id);
    }
    
    private void init(){
        id = 0l;
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);

        nombre = new TextField();
            Element.cfgComponent(nombre,"Nombre");
            nombre.setPlaceholder("Introduce el nombre del premio, sin acentos ");
        tituloPrecio = new Label("Precio");
            Element.cfgComponent(tituloPrecio);
            tituloPrecio.addStyleNames(ValoTheme.LABEL_SMALL);
        precio = new Slider(1,1000);
            precio.setOrientation(SliderOrientation.HORIZONTAL);
            precio.setWidth("100%");
            precio.isResponsive();
        tituloStock = new Label("Stock");
            Element.cfgComponent(tituloStock);
            tituloStock.addStyleNames(ValoTheme.LABEL_SMALL);
        stock = new Slider(1,1000);
            stock.setOrientation(SliderOrientation.HORIZONTAL);
            stock.setWidth("100%");
            stock.isResponsive();
        imagenLink = new TextField();
            Element.cfgComponent(imagenLink, "Dir Imagen");
            imagenLink.setPlaceholder("Introduce la direccion de imagen del premio");
        profesor = new ComboBox<>();
            Element.cfgComponent(profesor, "Profesor");
            profesor.setPlaceholder("Seleccione nombre profesor");
            profesor.setItems(ui.getControladorUsuario().getRepository().getByNombresLikeOrderByNombres("%"));

        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1, true, false);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(profesor);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(imagenLink);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(tituloPrecio);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(precio);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(tituloStock);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(stock);

        contentLayout.addComponent(content);
        setCaption("Premios");
        setWidth("50%");
    }

    @Override
    protected void loadData(Long id){
        try{
            Premio obj = ui.getControladorPremio().getRepository().findById(id).get();
            this.id = obj.getId();
            materia = obj.getObjMateria();
            nombre.setValue(obj.getNombre());
            precio.setValue(obj.getPrecio());
            stock.setValue(obj.getStock());
            imagenLink.setValue(obj.getImagenLink());
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            close();
        }
    }
    
    @Override
    protected void buttonAcceptEvent(){
        try{
            Premio obj = new Premio();
                obj.setId(id);
                obj.setMateria(materia.getId());
                obj.setNombre(nombre.getValue());
                obj.setProfesor(profesor.getValue()==null?0:profesor.getValue().getId());
                obj.setImagenLink(imagenLink.getValue());
                obj.setPrecio(precio.getValue().intValue());
                obj.setStock(stock.getValue().intValue());
                obj.setCreado(LocalDateTime.now());
            obj = ui.getControladorPremio().guardar(obj);
            if(obj != null){
                Element.makeNotification("Datos guardados correctamente",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                if(premioDlg != null) premioDlg.updateDlg();
                close();
            }
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }

    @Override protected void buttonCancelEvent(){close();}
    @Override protected void buttonDeleteEvent(){}}
