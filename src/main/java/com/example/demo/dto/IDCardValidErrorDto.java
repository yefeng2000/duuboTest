package com.example.demo.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RESPONSE")
public class IDCardValidErrorDto {

	@XmlElementWrapper(name="ROWS")
	@XmlElement(name="ROW")
	public List<RowDto> rows;
	
/*	@XmlAttribute(name="errorcode")
	private int errorCode;
	//@XmlAttribute
	private int code;
	//@XmlAttribute(name="countrows")
	private int countRows;*/

/*	public List<RowDto> getRows() {
		return rows;
	}

	public void setRows(List<RowDto> rows) {
		this.rows = rows;
	}*/

/*	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCountRows() {
		return countRows;
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
	}*/
}
