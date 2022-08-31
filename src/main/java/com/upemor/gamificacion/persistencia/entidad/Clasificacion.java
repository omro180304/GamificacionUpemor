
package com.upemor.gamificacion.persistencia.entidad;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Clasificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    private long alumno;
    
    @Column(nullable = false)
    private long actividad;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @Column(nullable = false)
    private long clasificacionTipo;

    /* Relations */
    @ManyToOne
    @JoinColumn(name = "alumno", referencedColumnName = "id", insertable = false, updatable = false)
    private Alumno objAlumno; 

    @ManyToOne
    @JoinColumn(name = "actividad", referencedColumnName = "id", insertable = false, updatable = false)
    private Actividad objActividad;
    
    @ManyToOne
    @JoinColumn(name = "clasificacionTipo", referencedColumnName = "id", insertable = false, updatable = false)
    private ClasificacionTipo objClasificacionTipo;
    
    /* Methods */
    public long getTotalPuntos() {    
        if (objActividad==null || objClasificacionTipo==null){
            return 0;
        }
        return Math.round(objActividad.getPuntaje()*objClasificacionTipo.getPonderacion());
    }

}
