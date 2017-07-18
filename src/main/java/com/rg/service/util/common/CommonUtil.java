package com.rg.service.util.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rg.service.entity.Money;

public class CommonUtil {
	public static final Logger log = Logger.getLogger(CommonUtil.class.getName());

	public static void main(String[] args) throws Exception {
		Money m = new Money();
		m.setAmount(506438.00);
		m.setInterestRate(11.24825);
		int totalInst = 60;
		m.setInstMonths(totalInst);
		m.setRemainInstMonths(totalInst);

		List<Map<String, String>> lsMap = getAmortizationSchedule(m, totalInst);
		for (Map<String, String> map : lsMap) {
			System.out.println(map);
		}
	}

	public static List<Map<String, String>> getAmortizationSchedule(Money money, int totalInst)
			throws CloneNotSupportedException {
		List<Map<String, String>> lsMap = new ArrayList<Map<String, String>>();
		DecimalFormat f = new DecimalFormat("##.00");

		Money m = null;
		Map<String, String> map = null;
		m = (Money) money.clone();
		for (int i = 1; i <= totalInst; i++) {
			map = new HashMap<String, String>();
			m.setRemainInstMonths(m.getInstMonths() - i);
			double interest = Double.valueOf(f.format(Money.getInterestForCurrentOutstanding(m)));
			double principal = m.getEmi() - interest;
			double balance = m.getAmount() - principal;
			if (i == totalInst) {
				if (balance < 0) {
					interest = interest - balance;
					principal = m.getEmi() - interest;
					balance = 0.0;
				}
			}
			map.put("p", f.format(principal));
			map.put("i", f.format(interest));
			if (balance == 0.0) {
				map.put("b", "0.00");
			} else {
				map.put("b", f.format(balance));
			}
			m.setAmount(balance);
			lsMap.add(map);
		}
		return lsMap;
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
