package tpgr06.coronatickets.ws.publicador;

import java.io.FileNotFoundException;
import java.io.IOException;

import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;

public class Publicador {

	/**
	 * @param args the command line arguments
	 * @throws NotFoundException
	 * @throws BadRequestException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main()
			throws BadRequestException, NotFoundException, FileNotFoundException, IOException {
		System.out.println("Publicando...");
		CoronaServices ws = new CoronaServices();
		ws.publicar();
		System.out.println("WS Corriendo...");
	}

}
