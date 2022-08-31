package com.upemor.gamificacion.vista.principal.usuarioLogin;

import com.smattme.MysqlExportService;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MysqlExportDatabase{
    
    public MysqlExportDatabase(){}
    
    public boolean createBackup(){
        //datos de acceso a la base de datos
        Properties properties = new Properties();
            properties.setProperty(MysqlExportService.DB_NAME, "gamificacion2");
            properties.setProperty(MysqlExportService.DB_USERNAME, "root");
            properties.setProperty(MysqlExportService.DB_PASSWORD, "");
            properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, "jdbc:mysql://localhost:3306/gamificacion2?serverTimezone=UTC");
            properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
            properties.setProperty(MysqlExportService.PRESERVE_GENERATED_SQL_FILE, "true");
                 
        try{
            MysqlExportService mysqlExportService = new MysqlExportService(properties);
                mysqlExportService.export();
                File file = mysqlExportService.getGeneratedZipFile();
                
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
        }
        return true;
    }
}
