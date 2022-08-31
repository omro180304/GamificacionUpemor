package com.upemor.gamificacion.utils;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.TextField;
import java.text.DecimalFormat;

/*
@author cerimice
@Company Tiamex
*/

public class IntegerField extends TextField{
    
    private DecimalFormat decimalFormat;
    
    public IntegerField(){
        super("","0");
        iniciarComponente();
    }
    
    public IntegerField(String valor){
        super("",valor);
        iniciarComponente();
    }
    
    private void iniciarComponente(){
        decimalFormat = new DecimalFormat("###,####,##0");
        //this.addValueChangeListener((ValueChangeEvent<String> event) -> {valueChangeEvent(event);});
        this.addFocusListener((FieldEvents.FocusEvent event) -> {focusListener();});
    }
    
    private void focusListener(){
        setSelection(0,getValue().length());
    }
    
    private void valueChangeEvent(ValueChangeEvent<String> event){
        try{
            Integer.parseInt(event.getValue().replace(",",""));
        }catch(NumberFormatException ex){
            setValue(event.getOldValue());
        }
    }
    
    public long getLongValue(){
        String value = getValue();
            value = value.replace(",","");
        return value.isEmpty()?0:Long.parseLong(value);
    }
    
    public int getIntValue(){
        String value = getValue();
            value = value.replace(",","");
        return value.isEmpty()?0:Integer.parseInt(value);
    }
    
    public void setValue(long valor){
        super.setValue(decimalFormat.format(valor));
    }
    
    public void setValue(int valor){
        super.setValue(decimalFormat.format(valor));
    }

}
