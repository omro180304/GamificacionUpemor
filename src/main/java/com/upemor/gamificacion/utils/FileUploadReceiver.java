package com.upemor.gamificacion.utils;

import com.vaadin.ui.Upload.Receiver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public final class FileUploadReceiver implements Receiver{
    private OutputStream outputFile;
    
    private File file;
    public File getFile(){return file;}
    
    public FileUploadReceiver(){
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType){
        try{
            file = new File(Utils.getTempFolder() + filename);
            if (!file.exists()){
                file.createNewFile();
            }
            outputFile = new FileOutputStream(file);
            return outputFile;
        }catch (IOException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
        }
        return null;
    }
    
    @Override
    protected void finalize(){
        try{
            super.finalize();
            if (outputFile != null){outputFile.close();}
        }catch(Throwable ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
        }
    }
}
