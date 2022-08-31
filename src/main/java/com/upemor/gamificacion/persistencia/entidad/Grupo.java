
package com.upemor.gamificacion.persistencia.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
@Entity
public class Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String generacion;

    @Column(nullable = false)
    private long turno;

    /* Relations */
    @ManyToOne
    @JoinColumn(name = "turno", referencedColumnName = "id", insertable = false, updatable = false)
    private Turno objTurno;

    @OneToMany(mappedBy = "objGrupo", cascade = CascadeType.REMOVE, orphanRemoval = true) 
    private List<GrupoAlumno> listGrupoAlumno;
    
    @OneToMany(mappedBy = "objGrupo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GrupoMateriaProfesor> listGrupoMateriaProfesor;

    /* Methods */
    @Override
    public String toString() {
        return nombre;
    }
    
    public List<Materia> getListaMaterias(){
        List<Materia> lista = new ArrayList<>();
        for(GrupoMateriaProfesor obj:listGrupoMateriaProfesor)
            lista.add(obj.getObjMateria());
        return lista;
    }
    
    public List<Materia> getListaMaterias(long profesorId){
        List<Materia> lista = new ArrayList<>();
        for(GrupoMateriaProfesor obj:listGrupoMateriaProfesor)
            if(obj.getObjProfesor().getId() == profesorId)lista.add(obj.getObjMateria());
        return lista;
    }
    
    public List<Usuario> getListaProfesores(){
        List<Usuario> lista = new ArrayList<>();
        for(GrupoMateriaProfesor obj:listGrupoMateriaProfesor)
            lista.add(obj.getObjProfesor());
        return lista;
    }
    
    public List<Alumno> getListaAlumnos(){
        List<Alumno> lista = new ArrayList<>();
        for(GrupoAlumno obj:listGrupoAlumno)
            lista.add(obj.getObjAlumno());
        return lista;
    }
}
