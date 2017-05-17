package com.occar.bean.rest;

import java.io.Serializable;

public class DBDataBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer rowCount;
	private String result;

	@Override
	public String toString() {
		return "DBDataBean [rowCount=" + rowCount + ", result=" + result + "]";
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
