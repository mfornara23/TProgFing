package com.tpgr06.coronatickets.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
	
	static String tomcatBase = System.getProperty("catalina.base");
	static String separator = File.separator;
	private static final String serverConfigPath = String.format("%s"+separator+"webapps"+separator+"corona-ticket-web"+separator+"server.properties", tomcatBase);
	private static Properties serverProp = null;

	public static Properties getInstance() {
		if (serverProp == null) {
			serverProp = new Properties();
			try {
				serverProp.load(new FileInputStream(serverConfigPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}

		return serverProp;
	}

}
