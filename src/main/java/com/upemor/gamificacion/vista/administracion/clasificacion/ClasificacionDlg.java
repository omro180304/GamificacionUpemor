package com.upemor.gamificacion.vista.administracion.clasificacion;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.persistencia.entidad.Clasificacion;
import com.upemor.gamificacion.reportes.pdf.ClasificacionPDF;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.utils.ShowPDFDlg;
import com.upemor.gamificacion.utils.TemplateDlg;
import com.vaadin.icons.VaadinIcons;
import com.upemor.gamificacion.utils.Utils;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;

public class ClasificacionDlg extends TemplateDlg<Clasificacion>{
    
    private Button crearReporte;
 
    public ClasificacionDlg(){
        init();
    }
    
    private void init(){
        
        searchField.setPlaceholder("Proporcione el nombre del alumno...");
       // grid.addComponentColumn(this::buildEliminarButton).setCaption("");
        grid.addColumn(Clasificacion::getId).setCaption("Id");
        grid.addColumn(Clasificacion::getObjAlumno).setCaption("Alumno");
        grid.addColumn(obj -> {return obj.getObjActividad().getObjUnidad().getObjMateria().toString();}).setCaption("Materia");
        grid.addColumn(obj -> {return obj.getObjActividad().getObjUnidad().toString();}).setCaption("Unidad");
        grid.addColumn(Clasificacion::getObjActividad).setCaption("Actividad");
        grid.addColumn(Clasificacion::getObjClasificacionTipo).setCaption("Tipo");
        grid.addColumn(Clasificacion::getTotalPuntos).setCaption("Total puntos");
        grid.addColumn(Clasificacion::getFecha).setCaption("Fecha");
        
        crearReporte = new Button("Crear PDF");
            Element.cfgComponent(crearReporte);
            crearReporte.addClickListener(event -> {eventoCrearReporte();});
        ResponsiveLayout acciones = new ResponsiveLayout();
            Element.cfgLayoutComponent(acciones);
        ResponsiveRow accionesRow1 = acciones.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            accionesRow1.addColumn().withDisplayRules(12,6,4,3).withComponent(crearReporte);
    contentLayout.addComponent(acciones);
        setCaption ("Clasificacion");
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            List<Clasificacion> lista = ui.getControladorClasificacion().getRepository().getByObjAlumno_nombresLike(searchField.getValue()+"%");
            grid.setItems(lista);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new ClasificacionImportarModalDlg());
    }

    @Override
    protected void eventEditButtonGrid(Clasificacion obj){
        //ui.addWindow(new ClasificacionModalDlg(obj.getId()));
    }
    
    private void eventoCrearReporte(){
        try{
            List<Clasificacion> lista = ui.getControladorClasificacion().getRepository().getByObjAlumno_nombresLike(searchField.getValue()+"%");
            ui.addWindow(
                new ShowPDFDlg(
                    new StreamResource(
                        new ClasificacionPDF(lista)
                        ,(Utils.getFormatIdLong()+".pdf").replace(" ", "")
                    )
                )
            );
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
    
    private Button buildEliminarButton(Clasificacion obj) {
        Button button = new Button("", VaadinIcons.MINUS_CIRCLE);
        button.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Eliminar unidad");
        button.addClickListener(e -> {
            ui.getControladorClasificacion().getRepository().delete(obj);
            updateDlg();
        });
        return button;
    }
}