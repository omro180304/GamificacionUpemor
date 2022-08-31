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

/*@author Archivos Propios*/

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UsuarioTipo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;

    /* Relations */
    @OneToMany(mappedBy = "objUsuarioTipo")
    private List<Usuario> listUsuarios;

    /* Methods */
    @Override
    public String toString(){return nombre;}
    
    public boolean isAdministrador(){return id==1;}
    public boolean isProfesor(){return id==2;}
}
