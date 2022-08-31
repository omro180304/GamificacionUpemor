package com.upemor.gamificacion.reportes.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.upemor.gamificacion.Gamificacion;
import com.upemor.gamificacion.persistencia.entidad.Clasificacion;
import com.upemor.gamificacion.utils.Utils;
import java.util.List;
import java.util.logging.Logger;

public final class ClasificacionPDF extends BasePDF{
    
    private final List<Clasificacion> listClasificacion;
    
    public ClasificacionPDF(List<Clasificacion> listClasificacion) throws Exception{
        this.listClasificacion = listClasificacion;
        
        create();
    }
    
    @Override
    protected void create() throws Exception{
        try{
            document = new Document();
                document.setMargins(15,15,15,15);
                document.setPageSize(PageSize.LETTER);
                document.addCreationDate();
                document.addTitle("Reporte de clasificación");
            PdfWriter writer = PdfWriter.getInstance(document,pdfFile);
            document.open();
            
            String imageFile = Gamificacion.getMainDirectory()+"logoUpemor.jpg";
            Image image = Image.getInstance(imageFile);
            
            PdfPTable tableTitle = new PdfPTable(new float[]{1f,3f});
                tableTitle.setWidthPercentage(95);
                tableTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableTitle.addCell(elementsPDF.getCell(image,Rectangle.BOTTOM,1,2,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                tableTitle.addCell(elementsPDF.getCell("Reporte de Clasificaciones",format.getFontTitleBold(),Rectangle.BOTTOM,1,2,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
            document.add(tableTitle);
            document.add(elementsPDF.getParagraph("\n".toUpperCase(),format.getFontTextSimple(),Element.ALIGN_CENTER));
            
            document.add(elementsPDF.getParagraph("Datos encontrados".toUpperCase(),format.getFontTitleBold(),Element.ALIGN_CENTER));
            document.add(elementsPDF.getParagraph("\n".toUpperCase(),format.getFontTextSimple(),Element.ALIGN_CENTER));
            int x = 1;
            PdfPTable tableOrderData = new PdfPTable(new float[]{1f,2f,2f,1f,1f,1f,1f,1f});
                tableOrderData.setWidthPercentage(95);
                tableOrderData.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableOrderData.addCell(elementsPDF.getCell("#",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                tableOrderData.addCell(elementsPDF.getCell("Alumno",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                tableOrderData.addCell(elementsPDF.getCell("Materia",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                tableOrderData.addCell(elementsPDF.getCell("Unidad",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                tableOrderData.addCell(elementsPDF.getCell("Actividad",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                tableOrderData.addCell(elementsPDF.getCell("Valor de Actividad",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                tableOrderData.addCell(elementsPDF.getCell("Tipo",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                tableOrderData.addCell(elementsPDF.getCell("Puntos Obtenidos",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                for(Clasificacion obj:listClasificacion){
                    tableOrderData.addCell(elementsPDF.getCell((x++)+"",format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                    tableOrderData.addCell(elementsPDF.getCell(obj.getObjAlumno().toString(),format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE));
                    tableOrderData.addCell(elementsPDF.getCell(obj.getObjActividad().getObjUnidad().getObjMateria().toString(),format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE));
                    tableOrderData.addCell(elementsPDF.getCell(obj.getObjActividad().getObjUnidad().toString(),format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE));
                    tableOrderData.addCell(elementsPDF.getCell(obj.getObjActividad().toString(),format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE));
                    tableOrderData.addCell(elementsPDF.getCell(integerFormat.format(obj.getObjActividad().getPuntaje()),format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_RIGHT,Element.ALIGN_MIDDLE));
                    tableOrderData.addCell(elementsPDF.getCell(obj.getObjClasificacionTipo().toString(),format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));                    
                    tableOrderData.addCell(elementsPDF.getCell(integerFormat.format(obj.getTotalPuntos()),format.getFontTextBold(),Rectangle.BOX,1,1,Element.ALIGN_RIGHT,Element.ALIGN_MIDDLE));
                }
                
            document.add(tableOrderData);
            document.add(elementsPDF.getParagraph("\n".toUpperCase(),format.getFontTextSimple(),Element.ALIGN_CENTER));
        }catch(DocumentException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }finally{
            document.close();
        }
    }
}
