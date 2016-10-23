/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uefs.papaleguasweb.exception;

/**
 *
 * @author netsrak
 */
public class NaoEhAdjacente extends Exception{
    
    public NaoEhAdjacente() {
        super ();
    }

    public NaoEhAdjacente(String message) {
        super(message);
    }

    public NaoEhAdjacente(String message, Throwable cause) {
        super(message, cause);
    }
}
