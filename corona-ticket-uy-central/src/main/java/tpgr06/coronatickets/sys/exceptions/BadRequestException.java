package tpgr06.coronatickets.sys.exceptions;

/**
 * Excepcion utilizada para indicar un error en la request
 *
 */

public class BadRequestException extends Exception {

    public BadRequestException(String string) {
        super(string);
    }
}
