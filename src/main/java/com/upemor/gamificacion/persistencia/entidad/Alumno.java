
package com.upemor.gamificacion.persistencia.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**/
@Getter
@Setter 
@NoArgsConstructor
@Entity
public class Alumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;
    
    @Column(nullable = false)
    private String correo;
    
    @Column(nullable = false)
    private String contrasena;
    
    @Column(nullable = false)
    private String palabraClave;
    
    @Column(nullable = false)
    private String matricula;
    
    @Column(nullable = false)
    private double saldo;
    
    /* Relations */ 
    @OneToMany(mappedBy = "objAlumno")
    private List<Clasificacion> listClasificacion;
    
    @OneToMany(mappedBy = "objAlumno")
    private List<GrupoAlumno> listGrupoAlumno;

    /* Methods */
    @Override
    public String toString() {
        return nombres+" "+apellidos;
    }
    
    public List<Grupo> getListaGrupos(){
        List<Grupo> lista = new ArrayList<>();
        for(GrupoAlumno grupoAlumno:listGrupoAlumno)
            lista.add(grupoAlumno.getObjGrupo());
        return lista;
    }
}
