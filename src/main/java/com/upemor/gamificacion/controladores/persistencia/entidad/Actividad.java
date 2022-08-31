
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
public class Actividad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private double puntaje;

    @Column(nullable = false)
    private long unidad;

    /* Relations */
    @ManyToOne
    @JoinColumn(name = "unidad", referencedColumnName = "id", insertable = false, updatable = false)
    private Unidad objUnidad;
    
    @OneToMany(mappedBy = "objActividad")
    private List<Clasificacion> listClasificacion;

    /* Methods */
    @Override
    public String toString() {
        return nombre;
    }
}
