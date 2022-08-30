package tpgr06.coronatickets.sys.exceptions;

/**
 * Excepcion utilizada para indicar la inexistencia en el sistema del objeto al que se hace referencia
 *
 */

public class NotFoundException extends Exception {

    public NotFoundException(String string) {
        super(string);
    }
}
