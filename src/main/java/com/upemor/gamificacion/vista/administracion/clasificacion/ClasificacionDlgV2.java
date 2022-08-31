package com.upemor.gamificacion.vista.administracion.clasificacion;

import com.upemor.gamificacion.persistencia.entidad.Clasificacion;
import com.upemor.gamificacion.persistencia.entidad.Actividad;
import com.upemor.gamificacion.reportes.pdf.ClasificacionPDF;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.persistencia.entidad.Grupo;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.upemor.gamificacion.utils.ShowPDFDlg;
import com.upemor.gamificacion.utils.Element;
import com.upemor.gamificacion.utils.Utils;
import com.vaadin.server.StreamResource;
import com.upemor.gamificacion.MainUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.DateTimeField;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import com.vaadin.ui.Alignment;
import java.util.logging.Level;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import java.util.ArrayList;
import com.vaadin.ui.Grid;
import java.util.List;

public class ClasificacionDlgV2 extends Panel{
    
    private final static int BUSQUEDA_GRUPO_MATERIA = 1;
    private final static int BUSQUEDA_ALUMNO_MATERIA = 2;
    private final static int BUSQUEDA_GRUPO_ACTIVIDAD = 3;
    private final static int BUSQUEDA_ALUMNO_ACTIVIDAD = 4;
    
    private MainUI ui;
    
    private ComboBox<Grupo> grupo;
    private ComboBox<Alumno> alumno;
    private DateTimeField fechaInicio;
    private DateTimeField fechaFinal;
    private ComboBox<Usuario> profesor;
    private ComboBox<Materia> materia;
    private ComboBox<Actividad> actividad;
    private Button btnSearch;
    private Button btnAdd;
    private Button crearReporte;

    private Grid<Clasificacion> grid;
    
    public ClasificacionDlgV2(){
        init();
    }
    
    private void init(){
        ui = Element.getUI();
        ResponsiveLayout content = new ResponsiveLayout();
            Element.cfgLayoutComponent(content);
            content.setSizeFull();
        fechaFinal = new DateTimeField();
            Element.cfgComponent(fechaFinal,"Fecha final");
            fechaFinal.setDateFormat("dd/MM/yyyy HH:mm");
            fechaFinal.setValue(LocalDateTime.now().withHour(23).withMinute(59).withSecond(59));
        fechaInicio = new DateTimeField();
            Element.cfgComponent(fechaInicio,"Fecha inicio");
            fechaInicio.setDateFormat("dd/MM/yyyy HH:mm");
            fechaInicio.setValue(fechaFinal.getValue().minusDays(23).withHour(0).withMinute(0).withSecond(0).withNano(0));
        grupo = new ComboBox<>();
            Element.cfgComponent(grupo,"Grupo");
            grupo.setEmptySelectionAllowed(true);
            grupo.setItems(ui.getControladorGrupo().getRepository().getByNombreLikeOrderByNombre("%"));
            grupo.addValueChangeListener(event -> {
                Grupo objGrupo = grupo.getValue();
                if(objGrupo == null){return;}
                alumno.setItems(objGrupo.getListaAlumnos());
                profesor.setItems(objGrupo.getListaProfesores());
            });
        alumno = new ComboBox<>();
            Element.cfgComponent(alumno,"Alumno");
            alumno.setEmptySelectionAllowed(true);
        profesor = new ComboBox<>();
            Element.cfgComponent(profesor,"Profesor");
            profesor.setEmptySelectionAllowed(true);
            profesor.addValueChangeListener(event -> {
                Usuario objProfesor = profesor.getValue();
                if(objProfesor == null){return;}
                Grupo objGrupo = grupo.getValue();
                materia.setItems(objGrupo.getListaMaterias(objProfesor.getId()));
            });
        materia = new ComboBox<>();
            Element.cfgComponent(materia,"Materia");
            materia.setEmptySelectionAllowed(true);
            materia.addValueChangeListener(event -> {
                Materia objMateria = materia.getValue();
                if(objMateria == null){return;}
                actividad.setItems(objMateria.getListaActividades());
            });
        actividad = new ComboBox<>();
            Element.cfgComponent(actividad,"Actividad");
            actividad.setEmptySelectionAllowed(true);
        btnSearch = new Button("Buscar");
            Element.cfgComponent(btnSearch);
            btnSearch.addClickListener((Button.ClickEvent event) -> {buttonSearchEvent();});
            
        btnAdd = new Button("Agregar");
            Element.cfgComponent(btnAdd);
            btnAdd.addStyleName(ValoTheme.BUTTON_DANGER);
            btnAdd.addClickListener((Button.ClickEvent event) -> {buttonAddEvent();});
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
        Element.cfgLayoutComponent(row1,true,false);
        row1.addColumn().withDisplayRules(12,6,6,2).withComponent(grupo);
        row1.addColumn().withDisplayRules(12,6,6,4).withComponent(alumno);
        row1.addColumn().withDisplayRules(12,6,6,3).withComponent(fechaInicio);
        row1.addColumn().withDisplayRules(12,6,6,3).withComponent(fechaFinal);
        row1.addColumn().withDisplayRules(12,6,6,4).withComponent(profesor);
        row1.addColumn().withDisplayRules(12,6,6,4).withComponent(materia);
        row1.addColumn().withDisplayRules(12,6,6,4).withComponent(actividad);
        row1.addColumn().withDisplayRules(12,6,6,3).withComponent(btnSearch);
        row1.addColumn().withDisplayRules(12,6,6,3).withComponent(btnAdd);
        
        grid = new Grid<>("<h2><b>Resultados encontrados</b></h2>");
            Element.cfgComponent(grid);
            grid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
            grid.setSelectionMode(Grid.SelectionMode.SINGLE);
            grid.addComponentColumn(this::buildEliminarButton).setCaption("");
            grid.addColumn(Clasificacion::getId).setCaption("Id");
            grid.addColumn(Clasificacion::getObjAlumno).setCaption("Alumno");
            grid.addColumn(obj -> {return obj.getObjActividad().getObjUnidad().getObjMateria().toString();}).setCaption("Materia");
            grid.addColumn(obj -> {return obj.getObjActividad().getObjUnidad().toString();}).setCaption("Unidad");
            grid.addColumn(obj -> {return obj.getObjActividad().getPuntaje();}).setCaption("Valor Actividad");
            grid.addColumn(Clasificacion::getObjActividad).setCaption("Actividad");
            grid.addColumn(Clasificacion::getObjClasificacionTipo).setCaption("Tipo");
            grid.addColumn(Clasificacion::getTotalPuntos).setCaption("Puntos obtenidos");
            grid.addColumn(Clasificacion::getFecha).setCaption("Fecha");
            
        crearReporte = new Button("Crear PDF");
            Element.cfgComponent(crearReporte);
            crearReporte.addClickListener(event -> {eventoCrearReporte();});
            
        ResponsiveRow row2 = content.addRow().withAlignment(Alignment.MIDDLE_CENTER);
            Element.cfgLayoutComponent(row2,true,false);
            row2.addColumn().withDisplayRules(12,12,12,12).withComponent(grid);
        
        ResponsiveLayout acciones = new ResponsiveLayout();
            Element.cfgLayoutComponent(acciones);
        ResponsiveRow accionesRow3 = acciones.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            accionesRow3.addColumn().withDisplayRules(12, 6, 4, 3).withComponent(crearReporte);

        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(content);
            main.addComponent(acciones);
            main.setSizeFull();
                
        this.setWidth("100%");
        this.setHeight("100%");
        this.setContent(main);
        this.setCaptionAsHtml(true);
        this.addStyleName("panel-friendly");
    }
    
    private Button buildEliminarButton(Clasificacion obj) {
        Button button = new Button("", VaadinIcons.MINUS_CIRCLE);
        button.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Eliminar Alumno");
        button.addClickListener(e -> {
            ui.getControladorClasificacion().getRepository().delete(obj);
            updateDlg();
        });
        return button;
    }
    
    public void updateDlg(){
        buttonSearchEvent();
        grid.recalculateColumnWidths();
    }
    
    private void buttonAddEvent(){
        ui.addWindow(new ClasificacionImportarModalDlg());
    }
    
    private List<Clasificacion> buttonSearchEvent(){
        //obtener los datos del rango de fechas, las listas, y los datos de entrada para los filtros
        LocalDateTime fecha1 = fechaInicio.getValue();
        LocalDateTime fecha2 = fechaFinal.getValue();
        List<Long> alumnos = new ArrayList<>();
        List<Long> actividades = new ArrayList<>();
        List<Clasificacion> lista = null;
        
        int tipoBusqueda = 0;
        Grupo objGrupo = grupo.getValue();
        Materia objMateria = materia.getValue();
        Alumno objAlumno = alumno.getValue();
        Actividad objActividad = actividad.getValue();
        if(objAlumno!=null && objActividad!=null) tipoBusqueda = BUSQUEDA_ALUMNO_ACTIVIDAD;
        else if(objGrupo!=null && objActividad!=null) tipoBusqueda = BUSQUEDA_GRUPO_ACTIVIDAD;
        else if(objAlumno!=null && objMateria!=null) tipoBusqueda = BUSQUEDA_ALUMNO_MATERIA;
        else if(objGrupo!=null && objMateria!=null) tipoBusqueda = BUSQUEDA_GRUPO_MATERIA;
        //creacion de menu de filtros para busqueda de la clasificacion
        switch(tipoBusqueda){//opciones
            case BUSQUEDA_GRUPO_MATERIA:// se muestra la informacion de acuerdo a la entrada 
                if(objGrupo == null){lista = new ArrayList<>(); break;}
                if(objMateria == null){lista = new ArrayList<>(); break;}//logica para mostrar datos de consulta
                for(Alumno objAlumno1:objGrupo.getListaAlumnos()){alumnos.add(objAlumno1.getId());}
                for(Actividad objActividad1:objMateria.getListaActividades()){actividades.add(objActividad1.getId());}
                lista = ui.getControladorClasificacion().getRepository().getByAlumnoInAndActividadInAndFechaBetween(alumnos,actividades,fecha1,fecha2);
                break;
            case BUSQUEDA_ALUMNO_MATERIA:
                if(objMateria == null){lista = new ArrayList<>(); break;}
                if(objAlumno == null){lista = new ArrayList<>(); break;}
                alumnos.add(objAlumno.getId());
                for(Actividad objActividad1:objMateria.getListaActividades()){actividades.add(objActividad1.getId());}
                lista = ui.getControladorClasificacion().getRepository().getByAlumnoInAndActividadInAndFechaBetween(alumnos,actividades,fecha1,fecha2);
                break;
            case BUSQUEDA_GRUPO_ACTIVIDAD:
                if(objGrupo == null){lista = new ArrayList<>(); break;}
                if(objActividad == null){lista = new ArrayList<>(); break;}
                for(Alumno objAlumno1:objGrupo.getListaAlumnos()){alumnos.add(objAlumno1.getId());}
                actividades.add(objActividad.getId());
                lista = ui.getControladorClasificacion().getRepository().getByAlumnoInAndActividadInAndFechaBetween(alumnos,actividades,fecha1,fecha2);
                break;
            case BUSQUEDA_ALUMNO_ACTIVIDAD:
                if(objAlumno == null){lista = new ArrayList<>(); break;}
                if(objActividad == null){lista = new ArrayList<>(); break;}
                alumnos.add(objAlumno.getId());
                actividades.add(objActividad.getId());
                lista = ui.getControladorClasificacion().getRepository().getByAlumnoInAndActividadInAndFechaBetween(alumnos,actividades,fecha1,fecha2);
                break;
            default:
                lista = new ArrayList<>();
                break;
        }
        grid.setItems(lista);
        return lista;
    }
    
    private void eventoCrearReporte() {
        try {
            List<Clasificacion> lista =buttonSearchEvent();
            ui.addWindow(
                    new ShowPDFDlg(
                            new StreamResource(
                                    new ClasificacionPDF(lista),
                                     (Utils.getFormatIdLong() + ".pdf").replace(" ", "")
                            )
                    )
            );
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            Element.makeNotification(ex.getMessage(), Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }
}
