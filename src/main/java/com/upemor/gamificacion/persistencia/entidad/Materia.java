
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

@Getter
@Setter 
@NoArgsConstructor
@Entity
public class Materia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;

    /* Relations */
    @OneToMany(mappedBy = "objMateria")
    private List<Unidad> listUnidades;
    
    @OneToMany(mappedBy = "objMateria")
    private List<Premio> listPremios;
    
    @OneToMany(mappedBy = "objMateria")
    private List<GrupoMateriaProfesor> listGrupoMateriaProfesor;

    /* Methods */
    @Override
    public String toString() {
        return nombre;
    }
    
    public List<Actividad> getListaActividades(){
        List<Actividad> lista = new ArrayList<>();
        for(Unidad unidad:listUnidades)
            for(Actividad actividad:unidad.getListActividades())
                lista.add(actividad);
        return lista;
    }
    
    public List<Premio> getListaPremios() {
        List<Premio> lista = new ArrayList<>();
            for (Premio premio : getListPremios()) {
                lista.add(premio);
            }
        
        return lista;
    }
    
    public List<Usuario> getListaProfesores(){
        List<Usuario> lista = new ArrayList<>();
        for(GrupoMateriaProfesor obj:listGrupoMateriaProfesor)
            lista.add(obj.getObjProfesor());
        return lista;
    }
}
