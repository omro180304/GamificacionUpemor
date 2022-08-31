
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
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;
    
    @Column(nullable = false)
    private String correo;
    
    @Column(nullable = false)
    private String contrasena;
    
    @Column(nullable = false)
    private String palabraClave;
    
    @Column(nullable = false)
    private long usuarioTipo;

    /* Relations */
    @ManyToOne
    @JoinColumn(name = "usuarioTipo", referencedColumnName = "id", insertable = false, updatable = false)
    private UsuarioTipo objUsuarioTipo;
    
    @OneToMany(mappedBy = "objProfesor")
    private List<GrupoMateriaProfesor> listGrupoMateriaProfesor;


    /* Methods */
    @Override
    public String toString() {
        return nombres;
    }
    
}
