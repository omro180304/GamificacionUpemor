
package com.upemor.gamificacion.persistencia.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**/
@Getter
@Setter 
@NoArgsConstructor
@Entity
public class GrupoMateriaProfesor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long grupo;

    @Column(nullable = false)
    private long materia;
    
    @Column(nullable = false)
    private long profesor;

    /* Relations */
    
    @ManyToOne
    @JoinColumn(name = "grupo", referencedColumnName = "id", insertable = false, updatable = false)
    private Grupo objGrupo;
    
    @ManyToOne
    @JoinColumn(name = "materia", referencedColumnName = "id", insertable = false, updatable = false)
    private Materia objMateria;
    
    @ManyToOne
    @JoinColumn(name = "profesor", referencedColumnName = "id", insertable = false, updatable = false)
    private Usuario objProfesor;

    /* Methods */
    //@Override
    /*public String toString() {
        return nombre;
    }*/
}
