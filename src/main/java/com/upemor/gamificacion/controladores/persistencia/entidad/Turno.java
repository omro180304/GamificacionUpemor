package com.upemor.gamificacion.persistencia.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Id;

/*@author Archivos Propios
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Turno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;

    /* Relations */
    @OneToMany(mappedBy = "objTurno")
    private List<Grupo> listGrupos;

    /* Methods */
    @Override
    public String toString() {
        return nombre;
    }
}
