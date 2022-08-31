package com.upemor.gamificacion.persistencia.entidad;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

@Getter
@Setter 
@NoArgsConstructor
@Entity
public class Canje implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private long alumno;
    
    @Column(nullable = false)
    private long premio;
    
    @Column(nullable = false)
    private long precio;
    
    /* Relations */
    @ManyToOne
    @JoinColumn(name = "premio", referencedColumnName = "id", insertable = false, updatable = false)
    private Premio objPremio;
    
    @ManyToOne
    @JoinColumn(name = "alumno", referencedColumnName = "id", insertable = false, updatable = false)
    private Alumno objAlumno;

}
