
package com.upemor.gamificacion.controladores;

import org.springframework.beans.factory.annotation.Autowired;

/* @author Archivos Propios
 */
public abstract class Controlador <T,R> {
    
    @Autowired private R repository;
    public R getRepository(){return repository;}
    
    protected abstract boolean validar(T obj) throws Exception;
    public abstract T guardar(T newer) throws Exception;
    
}