package com.upemor.gamificacion.reportes.pdf;

import com.itextpdf.text.Document;
import com.upemor.gamificacion.reportes.pdf.cfg.ElementsPDF;
import com.upemor.gamificacion.reportes.pdf.cfg.Format;
import com.upemor.gamificacion.utils.Utils;
import com.vaadin.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public abstract class BasePDF implements StreamResource.StreamSource{
    protected Format format;
    protected final ElementsPDF elementsPDF;
    
    protected ByteArrayOutputStream pdfFile;
    
    protected Document document;
    
    protected SimpleDateFormat dateFormat;
    protected SimpleDateFormat dateTimeFormat;
    protected DecimalFormat doubleFormat;
    protected DecimalFormat integerFormat;
    protected DecimalFormat currencyFormat;
    
    public BasePDF(){
        pdfFile = new ByteArrayOutputStream();
        
        format = new Format();
        elementsPDF = new ElementsPDF();
        
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        doubleFormat = new DecimalFormat("###,##0.00");
        integerFormat = new DecimalFormat("###,##0");
        currencyFormat = new DecimalFormat("$ ###,##0.00");
    }

    @Override
    public InputStream getStream(){
        return new ByteArrayInputStream(pdfFile.toByteArray());
    }
    
    public File getFile() throws Exception{
        try{
            File file;
            try (InputStream input = this.getStream()){
                file = new File(Utils.getTempFolder()+Utils.getFormatId().format(new Date())+".pdf");
                try (OutputStream salida = new FileOutputStream(file)){
                    byte[] buf =new byte[1024];
                    int len;
                    while((len=input.read(buf))>0){salida.write(buf,0,len);}
                }
            }
            return file;
        }catch (IOException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage()); throw ex;
        }
    }
    
    protected abstract void create() throws Exception;
}
