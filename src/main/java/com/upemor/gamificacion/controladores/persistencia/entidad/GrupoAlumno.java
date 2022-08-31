
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

@Getter
@Setter 
@NoArgsConstructor
@Entity
public class GrupoAlumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long grupo;

    @Column(nullable = false)
    private long alumno;
    
    /* Relations */
    @ManyToOne
    @JoinColumn(name = "grupo", referencedColumnName = "id", insertable = false, updatable = false)
    private Grupo objGrupo;

    @ManyToOne
    @JoinColumn(name = "alumno", referencedColumnName = "id", insertable = false, updatable = false)
    private Alumno objAlumno;
    
    /* Methods */
    //@Override
    //public String toString() {
    //    return nombre;
    //}
}
