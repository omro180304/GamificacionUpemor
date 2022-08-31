package com.upemor.gamificacion.utils;

import com.upemor.gamificacion.MainUI;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;

public final class FileUploadProgress implements ProgressListener{
    
    /** Maxsize in bytes**/
    private long maxSize;
    public void setMaxSize(long sizeInKB){
        maxSize = 1024*sizeInKB;
    }
    
    private final Upload upload;
    
    public FileUploadProgress(Upload component){
        upload = component;
        setMaxSize(5500);
    }
    
    @Override
    public void updateProgress(long readBytes, long contentLength){
        if(contentLength > maxSize){
            upload.interruptUpload();
            MainUI ui = Element.getUI();
            Element.makeNotification("El tamaño del archivo ha sido excedido",Notification.Type.WARNING_MESSAGE,Position.TOP_CENTER).show(upload.getUI().getPage());
        }
    }
    
}
