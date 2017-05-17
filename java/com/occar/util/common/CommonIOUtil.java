/**
 * 
 */
package com.occar.util.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ramnath
 * Util class for file CRUD operations
 */
public class CommonIOUtil {

	private static final Logger log = Logger.getLogger(CommonIOUtil.class.getName());
	public static void main(String[] args) {

	}

	/**
	 * creates a file for the given byte array and fully qualified file name
	 * @param content
	 * @param fileName
	 */
	public static void writeFile(byte[] content, String fileName) {
		File file = null;
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile(); 
			}
			log.info("path stuff="+file.getAbsolutePath());
			fos = new FileOutputStream(file);
			fos.write(content);
			fos.flush();
			
			is = new BufferedInputStream(new FileInputStream(file));
			String contentType = URLConnection.guessContentTypeFromStream(is);
			log.info("contentType :: " + contentType);
		} catch (IOException e) {
			log.log(Level.SEVERE, "File Exception while creating or writing", e);
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				log.log(Level.SEVERE, "File Exception while closing output stream", e);
			}
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				log.log(Level.SEVERE, "File Exception while closing input stream", e);
			}
		}
	}
	
	/**
	 * Deletes a file denoted by the fully qualified file name
	 * @param fileName
	 */
	public static void deleteFile(String fileName){
		// Using Paths and Files class utils here so that this will work in any file system
		Path path = Paths.get(fileName);
		log.info("File to be deleted: "+ path);
		try {
			Files.delete(path);
		} catch (NoSuchFileException e){
			log.info("No document exist for this user for the given document id");
		} catch (IOException e) {
			log.log(Level.SEVERE, "Exception while trying to delete the file :- "+ fileName, e);
		}
	}
}
