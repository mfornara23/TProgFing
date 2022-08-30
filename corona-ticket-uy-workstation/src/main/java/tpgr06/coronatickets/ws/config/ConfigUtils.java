package tpgr06.coronatickets.ws.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {

	private final static String serverConfigPath = "." + File.separator + "src" + File.separator + "main"
			+ File.separator + "resources" + File.separator + "ws" + File.separator + "config" + File.separator
			+ "server.properties";
	private final static String serverConfigPath2 = "." + File.separator + "ws"  + File.separator + "config" + File.separator + "server.properties";
	private static Properties serverProp = null;

	public static Properties getInstance() throws FileNotFoundException, IOException {
		if (serverProp == null) {
			serverProp = new Properties();
			File propFile = new File(serverConfigPath);
			if (!propFile.exists()) {
				serverProp.load(new FileInputStream(serverConfigPath2));
			} else {
				serverProp.load(new FileInputStream(serverConfigPath));
			}
			
		}

		return serverProp;
	}

}
