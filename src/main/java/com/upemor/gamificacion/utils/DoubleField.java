package com.upemor.gamificacion.utils;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.TextField;
import java.text.DecimalFormat;
import java.text.ParseException;

/*
@author cerimice
@Company Tiamex
*/

public class DoubleField extends TextField{
    
    private DecimalFormat decimalFormat;
    public void setDecimalFormat(DecimalFormat format){decimalFormat = format;}
    
    public DoubleField(){
        super("","0.00");
        iniciarComponente();
    }
    
    public DoubleField(String valor){
        super("",valor);
        iniciarComponente();
    }
    
    private void iniciarComponente(){
        decimalFormat = new DecimalFormat("###,####,##0.00");
        //this.addValueChangeListener((ValueChangeEvent<String> event) -> {valueChangeEvent(event);});
        this.addFocusListener((FieldEvents.FocusEvent event) -> {focusListener();});
    }
    
    private void valueChangeEvent(ValueChangeEvent<String> event){
        try{
            Double.parseDouble(event.getValue().replace(",",""));
        }catch(NumberFormatException ex){
            setValue(event.getOldValue());
        }
    }
    
    private void focusListener(){
        setSelection(0,getValue().length());
    }
    
    public double getDoubleValue(){
        try{
            String value = getValue();
            return value.isEmpty()?0:decimalFormat.parse(value).doubleValue();
        }catch(ParseException ex){
            return 0;
        }
    }
    
    public float getFloatValue(){
        try{
            String value = getValue();
            return value.isEmpty()?0:decimalFormat.parse(value).floatValue();
        }catch(ParseException ex){
            return 0;
        }
    }
    
    public void setValue(double valor){
        super.setValue(decimalFormat.format(valor));
    }
    
    public void setValue(float valor){
        super.setValue(decimalFormat.format(valor));
    }
}
