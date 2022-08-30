package com.tpgr06.coronatickets.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ImgUtils {
	
	static String tomcatBase = System.getProperty("catalina.base");
	static String separator = File.separator;
	private static final String SAVING_PATH = String.format("%s"+separator+"webapps"
			+separator+"corona-ticket-web"+separator+"media"+separator+"img", tomcatBase);
	
	/**
	 * Guarda imagen y devuelve el path
	 * @param fileInput Imagen
	 * @return Path a la imagen
	 */
	public static String saveFile(InputStream fileInput) {		
		File uploads = new File(SAVING_PATH);
		if (!uploads.exists()){
			uploads.mkdirs();
		}
		File file = new File(uploads, System.currentTimeMillis()+".png");

		try {
			Files.copy(fileInput, file.toPath());
		} catch (IOException  e) {
			System.out.println(e.getMessage());
		}
		
		return file.getPath().substring(file.getPath().indexOf(separator+"media"),file.getPath().indexOf(".png")+4).replace(separator, "/");
	}
	
}
