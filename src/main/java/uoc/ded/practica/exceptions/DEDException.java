package main.java.uoc.ded.practica.exceptions;

/**
 * Excepció específica del gestor.
 *
 * @author Equip docent d'Estructura de la Informació de la UOC
 * @version Tardor 2006
 */
public class DEDException extends Exception {

    private static final long serialVersionUID = -2577150645305791318L;

    public DEDException() {
        super();
    }

    public DEDException(String msg) {
        super(msg);
    }

}
