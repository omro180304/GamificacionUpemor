package com.upemor.gamificacion.reportes.pdf.cfg;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.upemor.gamificacion.utils.Utils;
import java.io.IOException;
import java.util.logging.Logger;

public class FormatoPagina extends PdfPageEventHelper{
    private final long idCompany;
    
    public FormatoPagina(long idCompany) throws IOException, Exception{
        this.idCompany = idCompany;
    }

    @Override
    public void onStartPage (PdfWriter writer, Document document){
        try{
            //File fondo = new File(Gamificacion.getMainDirectory()+"/logoMailMax.png");
            //if(!fondo.exists()){fondo = new File(Gamificacion.getMainDirectory()+"logoMailMax.png");}
            
            //String pathFondoPagina = fondo.getPath();
            //Image fondoPagina = Image.getInstance(pathFondoPagina);
                //fondoPagina.scaleAbsolute(document.getPageSize());
                //fondoPagina.setAbsolutePosition(0,0);
            PdfContentByte canvas = writer.getDirectContentUnder();
                //canvas.addImage(fondoPagina);
        }catch (Exception ex){
            Logger.getLogger(FormatoPagina.class.getName()).log(Utils.nivelLoggin(), null, ex);
        }
    }
    
    @Override
    public void onEndPage (PdfWriter writer, Document document){
    }
}
