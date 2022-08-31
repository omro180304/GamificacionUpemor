package com.upemor.gamificacion.controladores;
//Importacion de las librerias a usar
import com.upemor.gamificacion.persistencia.repositorio.RepositorioClasificacion;
import com.upemor.gamificacion.persistencia.entidad.ClasificacionTipo;
import com.upemor.gamificacion.persistencia.entidad.Clasificacion;
import com.upemor.gamificacion.persistencia.entidad.Actividad;
import org.springframework.beans.factory.annotation.Autowired;
import com.upemor.gamificacion.persistencia.entidad.Materia;
import com.upemor.gamificacion.persistencia.entidad.Alumno;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ControladorClasificacion extends Controlador<Clasificacion,RepositorioClasificacion>{
    
    @Autowired private ControladorAlumno controladorAlumno;
    @Autowired private ControladorActividad controladorActividad;
    @Autowired private ControladorClasificacionTipo controladorClasificacionTipo;
    
    @Override//metodo que genera una excepcion en caso de que alguna de las validaciones 
    //no se cumpla y devuelve true cuando se cumple con las sentencias
    protected boolean validar(Clasificacion obj) throws Exception {
        try{
            if(obj.getActividad() <= 0 ) { throw new Exception("Actividad no valida");}
            if(obj.getAlumno() <= 0 ) { throw new Exception("Alumno no valida");}
            if(obj.getClasificacionTipo()<= 0 ) { throw new Exception("Tipo de clasificación no valido");}
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
          throw ex;
        }
    }

    @Override//metodo para guardar los datos de la tabla en el modulo de clasificacion
    // genera el id en caso de no existir el registro y salva los cambios en caso de haber
    public Clasificacion guardar(Clasificacion newer) throws Exception {
        try {
            if (validar(newer)){
                if (newer.getId() == 0){
                    return getRepository().save(newer);
                }
                Clasificacion original = getRepository().findById(newer.getId()).get();
                    original.setClasificacionTipo(newer.getClasificacionTipo());
                return getRepository().save(original);
            }
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
        return null;
    }
    
    //Metodo para capturar los datos del archivo excel al sistema
    public boolean importarDesdeCSV(String archivo,String separador, Materia materia) throws Exception{
        //recibe los datos y el separador que es la coma (,)
        try{
            //lectura del archivo linea por linea
            List<String> listData = Files.readAllLines(Paths.get(archivo));
            //separar y guardar encabezados
            String encabezados[] = listData.get(0).split(separador);
            listData.remove(0);
           
            for (String data : listData){
                String dts[] = data.split(separador);
                for(int pos=1;pos<encabezados.length;pos++){
                    Alumno alumno = controladorAlumno.getRepository().getByMatricula(dts[0]);
                    if(alumno != null){//si el alumno existe sigue recorriendo
                        ClasificacionTipo clasificacionTipo = controladorClasificacionTipo.getRepository().findById(Long.parseLong(dts[pos])).get();
                        if(clasificacionTipo != null){// si el tipo de clasificacion es valido sigue recorriendo
                            Actividad actividad = controladorActividad.getRepository().getByNombreLikeAndObjUnidad_materia(encabezados[pos]+"%",materia.getId());
                            if (actividad != null){// si la actividad es valida continua
                                //a guardar los datos en la tabla clasificacion
                                Clasificacion obj = new Clasificacion();
                                    obj.setAlumno(alumno.getId());
                                    obj.setActividad(actividad.getId());
                                    obj.setClasificacionTipo(clasificacionTipo.getId());
                                    obj.setFecha(LocalDateTime.now());
                                obj=guardar(obj);
                                obj = getRepository().findById(obj.getId()).get();
                                //guarda los datos del objeto
                                if(obj!=null){//de ser correcto actualiza los puntos del alumno en su saldo
                                    alumno.setSaldo(alumno.getSaldo()+obj.getTotalPuntos());
                                    controladorAlumno.guardar(alumno);
                                }
                            }
                        }
                    }
                }
            }
            return true;//devuelve true cuando se completo el recorrido del archivo
        }catch (Exception ex){//de lo contrario devuelve el error en consola
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
            throw ex;
        }
    }
}