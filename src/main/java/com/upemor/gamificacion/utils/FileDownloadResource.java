package com.upemor.gamificacion.utils;

import com.vaadin.server.DownloadStream;
import com.vaadin.server.FileResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class FileDownloadResource extends FileResource{
    
    public FileDownloadResource(File sourceFile){
        super(sourceFile);
    }
    
    @Override
    public DownloadStream getStream(){
        try{
            final DownloadStream ds = new DownloadStream(new FileInputStream(getSourceFile()),getMIMEType(),getFilename());
            ds.setParameter("Content-Disposition", "attachment; filename=" + getFilename());
            ds.setCacheTime(getCacheTime());
            return ds;
        }catch(final FileNotFoundException ex){
        Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
        return null;
        }
    }
}
