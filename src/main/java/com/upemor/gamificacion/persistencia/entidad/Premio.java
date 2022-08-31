
package com.upemor.gamificacion.persistencia.entidad;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
@Entity
public class Premio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private double precio;
    
    @Column(nullable = false)
    private double stock;
    
    @Column(nullable = false)
    private String imagenLink;

    @Column(nullable = false)
    private LocalDateTime creado;
    
    @Column(nullable = false)
    private long materia;
    
    @Column(nullable = false)
    private long profesor;

    /* Relations */
    @ManyToOne
    @JoinColumn(name = "materia", referencedColumnName = "id", insertable = false, updatable = false)
    private Materia objMateria;
    
    @ManyToOne
    @JoinColumn(name = "profesor", referencedColumnName = "id", insertable = false, updatable = false)
    private Usuario objProfesor;


    /* Methods */
    @Override
    public String toString() {
        return nombre;
    }

}
