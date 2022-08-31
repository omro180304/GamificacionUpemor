
package com.upemor.gamificacion.persistencia.entidad;

import java.io.Serializable;
import java.util.List;
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

/**/
@Getter
@Setter 
@NoArgsConstructor
@Entity
public class Unidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private long materia;

    /* Relations */
    @ManyToOne
    @JoinColumn(name = "materia", referencedColumnName = "id", insertable = false, updatable = false)
    private Materia objMateria;
    
    @OneToMany(mappedBy = "objUnidad")
    private List<Actividad> listActividades;

    /* Methods */
    @Override
    public String toString() {
        return nombre;
    }
}
