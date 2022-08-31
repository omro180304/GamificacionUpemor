package com.upemor.gamificacion.utils;

import lombok.Getter;

/** @author cerimice **/

@Getter
public enum Month{
    JANUARY(1,"Enero"),
    FEBRUARY(2,"Febrero"),
    MARCH(3,"Marzo"),
    APRIL(4,"Abril"),
    MAY(5,"Mayo"),
    JUNE(6,"Junio"),
    JULY(7,"Julio"),
    AUGUST(8,"Agosto"),
    SEPTEMBER(9,"Septiembre"),
    OCTOBER(10,"Octubre"),
    NOVEMBER(11,"Noviembre"),
    DECEMBER(12,"Diciembre");
    
    private long id;
    private String name;
    
    private Month(long id,String name){
        this.id = id;
        this.name = name;
    }
    
    @Override
    public String toString(){return name;}
    
    public static Month getById(int id){
        Month obj = null;
        for(Month month:Month.values()) if(month.getId() == id ) obj = month;
        return obj;
    }
    
}
