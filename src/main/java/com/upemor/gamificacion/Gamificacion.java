
package com.upemor.gamificacion;

import java.io.File;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@author Archivos Propios
 */
@SpringBootApplication
public class Gamificacion{
    
    public static String getMainDirectory(){
        return "./media/";
    }
    
    private static void createWorkDirectory(){
        File file = new File("./media");
            if(!file.exists()){file.mkdir();}
    }
    
    public static void main(String[] args){
        createWorkDirectory();
        SpringApplication.run(Gamificacion.class, args);
    }
}
