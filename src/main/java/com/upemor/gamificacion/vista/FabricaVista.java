package com.upemor.gamificacion.vista;

import com.upemor.gamificacion.vista.administracion.alumno.AlumnoDlg;
import com.upemor.gamificacion.vista.administracion.clasificacion.ClasificacionDlg;
import com.upemor.gamificacion.vista.administracion.clasificacion.ClasificacionDlgV2;
import com.upemor.gamificacion.vista.administracion.materia.MateriaDlg;
import com.upemor.gamificacion.vista.administracion.usuario.UsuarioDlg;
import com.upemor.gamificacion.vista.administracion.grupo.GrupoDlg;
import com.upemor.gamificacion.vista.principal.alumnoLogin.AlumnoLogin;
import com.upemor.gamificacion.vista.principal.alumnoLogin.AlumnoRecupera;
import com.upemor.gamificacion.vista.principal.usuarioLogin.UsuarioVista;
import com.upemor.gamificacion.vista.principal.alumnoLogin.AlumnoVista;
import com.upemor.gamificacion.vista.principal.alumnoLogin.AlumnoRegistra;
import com.upemor.gamificacion.vista.principal.usuarioLogin.UsuarioRecupera;
import com.upemor.gamificacion.vista.principal.InicialDlg;

public class FabricaVista{
    
    private InicialDlg inicialDlg;
    public InicialDlg getInicialDlg() {
        if (inicialDlg == null) {
            inicialDlg = new InicialDlg();
        }
        return inicialDlg;
    }
    
    private UsuarioVista usuarioVista;
    public UsuarioVista getUsuarioVista(){
        if(usuarioVista == null) usuarioVista = new UsuarioVista();
        return usuarioVista;
    }
    
    private AlumnoVista alumnoVista;
    public AlumnoVista getAlumnoVista(){
        if(alumnoVista == null) alumnoVista = new AlumnoVista();
        return alumnoVista;
    }
    
    private AlumnoRecupera alumnoRecupera;
    public AlumnoRecupera getAlumnoRecupera(){
        if(alumnoRecupera == null) alumnoRecupera = new AlumnoRecupera();
        return alumnoRecupera;
    }
    
    
    private UsuarioRecupera usuarioRecupera;
    public UsuarioRecupera getUsuarioRecupera() {
        if (usuarioRecupera == null) {
            usuarioRecupera = new UsuarioRecupera();
        }
        return usuarioRecupera;
    }
    
    private AlumnoRegistra alumnoRegistra;
    public AlumnoRegistra getAlumnoRegistra() {
        if (alumnoRegistra == null) {
            alumnoRegistra = new AlumnoRegistra();
        }
        return alumnoRegistra;
    }
    
    private UsuarioDlg usuarioDlg;
    public UsuarioDlg getUsuarioDlg(){
        if(usuarioDlg == null) usuarioDlg = new UsuarioDlg();
        usuarioDlg.updateDlg();
        return usuarioDlg;
    }
    
    private AlumnoDlg alumnoDlg;
    public AlumnoDlg getAlumnoDlg() {
        if (alumnoDlg == null) {
            alumnoDlg = new AlumnoDlg();
        }
        alumnoDlg.updateDlg();
        return alumnoDlg;
    }
    
    private AlumnoLogin alumnoLogin;
    public AlumnoLogin getAlumnoLogin() {
        if (alumnoLogin == null) {
            alumnoLogin = new AlumnoLogin();
        }
        //alumnoLogin.updateDlg();
        return alumnoLogin;
    }
    
    private MateriaDlg materiaDlg;
    public MateriaDlg getMateriaDlg() {
        if (materiaDlg == null) {
            materiaDlg = new MateriaDlg();
        }
        materiaDlg.updateDlg();
        return materiaDlg;
    }
    
    private GrupoDlg grupoDlg;

    public GrupoDlg getGrupoDlg() {
        if (grupoDlg == null) {
            grupoDlg = new GrupoDlg();
        }
        grupoDlg.updateDlg();
        return grupoDlg;
    }

    ClasificacionDlg clasificacionDlg;
    public ClasificacionDlg getClasificacionDlg() {
        if (clasificacionDlg == null) {
            clasificacionDlg = new ClasificacionDlg();
        }
        clasificacionDlg.updateDlg();
        return clasificacionDlg;
    }
    
    ClasificacionDlgV2 clasificacionDlgV2;
    public ClasificacionDlgV2 getClasificacionDlgV2() {
        if (clasificacionDlgV2 == null) {
            clasificacionDlgV2 = new ClasificacionDlgV2();
        }
        clasificacionDlgV2.updateDlg();
        return clasificacionDlgV2;
    }
    
}
