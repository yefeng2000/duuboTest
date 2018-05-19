package com.example.demo.service;

public interface IDCardValidService {

	/**
	 * 核查方法
	 * @param inLincense
	 * @param inConditions
	 * @return
	 */
	String nciicCheck(String inLincense, String inConditions);
	
	/**
	 * 取得条件文件模板
	 * @param inLincense
	 * @return
	 * @throws Exception
	 */
	String nciicGetCondition(String inLincense) throws Exception;
}
