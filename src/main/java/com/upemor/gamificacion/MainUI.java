
package com.upemor.gamificacion;

import com.upemor.gamificacion.controladores.ControladorGrupoMateriaProfesor;
import com.upemor.gamificacion.controladores.ControladorClasificacion;
import com.upemor.gamificacion.controladores.ControladorUsuarioTipo;
import com.upemor.gamificacion.controladores.ControladorGrupoAlumno;
import com.upemor.gamificacion.controladores.ControladorActividad;
import com.upemor.gamificacion.controladores.ControladorUsuario;
import com.upemor.gamificacion.controladores.ControladorMateria;
import com.upemor.gamificacion.controladores.ControladorAlumno;
import com.upemor.gamificacion.controladores.ControladorPremio;
import com.upemor.gamificacion.controladores.ControladorUnidad;
import com.upemor.gamificacion.controladores.ControladorGrupo;
import com.upemor.gamificacion.controladores.ControladorCanje;
import org.springframework.beans.factory.annotation.Autowired;
import com.upemor.gamificacion.controladores.ControladorTurno;
import com.upemor.gamificacion.persistencia.entidad.Usuario;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import com.upemor.gamificacion.vista.principal.InicialDlg;
import com.upemor.gamificacion.vista.FabricaVista;
import com.vaadin.annotations.PreserveOnRefresh;
import com.upemor.gamificacion.utils.Element;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.UI;
import lombok.Getter;
import lombok.Setter;

@SpringUI
@PreserveOnRefresh
@Viewport("width=device-width, initial-scale=1")
@Theme("mainTheme")
@Getter
public class MainUI extends UI {
    
    /* Acceso a los controladores */
    @Autowired private ControladorAlumno controladorAlumno;
    @Autowired private ControladorGrupoAlumno controladorGrupoAlumno;
    @Autowired private ControladorGrupoMateriaProfesor controladorGrupoMateriaProfesor;
    @Autowired private ControladorMateria controladorMateria;
    @Autowired private ControladorUsuario controladorUsuario;
    @Autowired private ControladorClasificacion controladorClasificacion;
    @Autowired private ControladorPremio controladorPremio;
    @Autowired private ControladorGrupo controladorGrupo;
    @Autowired private ControladorCanje controladorCanje;
    @Autowired private ControladorUsuarioTipo controladorUsuarioTipo;
    @Autowired private ControladorTurno controladorTurno;
    @Autowired private ControladorUnidad controladorUnidad;
    @Autowired private ControladorActividad controladorActividad;
    
    
    /* Variables de sesion */
    private FabricaVista fabricaVista;
    @Setter private Usuario usuario;
    @Setter private Alumno alumno;

    @Override
    protected void init(VaadinRequest request){
        fabricaVista = new FabricaVista();
        
        setSizeFull();
        getUI().getPage().setTitle(Element.getSystemName());
        setContent(new InicialDlg());
    }    
}
