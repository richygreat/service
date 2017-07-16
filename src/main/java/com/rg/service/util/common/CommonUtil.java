package com.rg.service.util.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rg.service.entity.Money;

public class CommonUtil {
	public static final Logger log = Logger.getLogger(CommonUtil.class.getName());

	public static void main(String[] args) throws Exception {
		Money m = new Money();
		m.setAmount(191913.31);
		m.setInterestRate(11.25);
		System.out.println(Money.getInterestForInstIndex(m, 1));
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
