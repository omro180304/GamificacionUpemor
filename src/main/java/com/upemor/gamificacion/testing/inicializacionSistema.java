package com.upemor.gamificacion.testing;

import com.upemor.gamificacion.controladores.ControladorAlumno;
import com.upemor.gamificacion.controladores.ControladorActividad;
import com.upemor.gamificacion.controladores.ControladorUnidad;
import com.upemor.gamificacion.vista.principal.usuarioLogin.MysqlExportDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class inicializacionSistema implements ApplicationRunner{
    @Autowired private CrearTipoDeUsuario crearTipoDeUsuario;
    @Autowired private CrearTipoDeClasificacion crearTipoDeClasificacion;
    @Autowired private CrearTurno crearTurno;
    @Autowired private CrearMateria crearMateria;
    @Autowired private CrearGrupo crearGrupo;
    @Autowired private crearUsuario crearUsuario;
    @Autowired private crearAlumno crearAlumno;
    @Autowired private ControladorAlumno controladorAlumno;
    @Autowired private ControladorActividad controladorActividad;
    @Autowired private ControladorUnidad controladorUnidad;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        crearTipoDeUsuario.crearDatos();
        crearTurno.crearDatos();
        crearMateria.crearDatos();
        crearGrupo.crearDatos();
        crearTipoDeClasificacion.crearDatos();
        crearUsuario.crearDatos();
        //crearAlumno.crearDatos();
    }
}
