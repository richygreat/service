package com.rg.service.util.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

public class CommonUtil {
	public static final Logger log = Logger.getLogger(CommonUtil.class.getName());

	public static void main(String[] args) throws Exception {
		String httpsURL = "https://www.google.co.in";
		URL myurl = new URL(httpsURL);
		HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
		InputStream ins = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(ins);
		BufferedReader in = new BufferedReader(isr);

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
		}

		in.close();
	}

	public static String getCSVFromList(List<String> ls) {
		final StringBuilder sb = new StringBuilder();
		boolean start = true;
		for (String str : ls) {
			if (start) {
				sb.append(str);
				start = false;
			} else {
				sb.append(",").append(str);
			}
		}
		return sb.toString();
	}

	public static String getOrderedStringFromDate(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	public static boolean isNullOrBlank(String str) {
		return (str == null || str.trim().equals(""));
	}

	public static void writeFile(byte[] content, String fileName) {
		File file = null;
		FileOutputStream fos = null;
		try {
			file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			fos.write(content);
			fos.flush();
		} catch (IOException e) {
			log.log(Level.SEVERE, "File Exception while creating or writing", e);
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				log.log(Level.SEVERE, "File Exception while closing stream", e);
			}
		}
	}

	public static String getConcatenatedString(String... args) {
		StringBuffer sb = new StringBuffer();
		for (String str : args) {
			sb.append(str);
		}
		return sb.toString();
	}
}
