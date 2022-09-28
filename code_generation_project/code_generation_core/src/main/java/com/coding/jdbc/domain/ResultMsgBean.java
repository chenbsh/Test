package com.coding.jdbc.domain;

import com.coding.jdbc.exception.SYHCException;

/**
 * 软件程序结果集信息统一处理类<br/>
 * <p>
 * 业务信息控制阀门主要通过结果标记位 flag 来执行<br/>
 * <p>
 * 正常返回=NORMAL，正常返回但结果为空=NULLRESULT，正常返回但执行失败=FAIL,非正常返回，一般是发生异常情况=EXCEPTION）<br/>
 * <p>
 * 提供了四种构造函数，非常方便调用<br/>
 * <p>
 * 1、ResultMsgBean(int flag)，经常用在INSERT、UPDATE、DELETE正常执行情况下<br/>
 * <p>
 * 2、ResultMsgBean(Object result, int length)，经常用在数据分页查询正常执行情况下<br/>
 * <p>
 * 3、ResultMsgBean(Object result) ，经常用在单条查询正常执行情况下<br/>
 * 
 * 
 * @Copyright MacChen
 * 
 * @Project CodeGenerationTool
 * 
 * @Author MacChen
 * 
 * @timer 2017-12-01
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 8.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class ResultMsgBean<T> extends BasicBean {

	/**
	 * 正常返回，正确执行SELECT语句但没有获取到数据
	 */
	public static final int NULLRESULT = 2;
	/**
	 * 正常返回
	 */
	public static final int NORMAL = 1;
	/**
	 * 可预见异常：存在异常代码
	 */
	public static final int CATCHEXCEPTION = -1;
	/**
	 * 运行时异常：不存在异常代码，但返回异常详细信息
	 */
	public static final int RUNTIMEEXCEPTION = -2;
	private static final long serialVersionUID = 2715637553932969716L;
	/**
	 * 结果标志
	 */
	private int flag;
	/**
	 * 返回结果集
	 */
	private T result;
	/**
	 * 错误代码
	 */
	private int errorCode;

	/**
	 * 消息提示
	 */
	private String message;
	/**
	 * 如果需要返回总记录数时返回
	 */
	private int length = 0;

	/**
	 * 构造函数：一般用在查询数据明细（单条）情况
	 */
	public ResultMsgBean(T result) {
		if (result != null) {
			this.flag = NORMAL;
			this.result = result;
		} else {
			this.flag = NULLRESULT;
			this.result = null;
		}
	}

	/**
	 * 构造函数：一般用在数据分页查询情况
	 * <p>
	 * 第一个参数是查询结果集
	 * <p>
	 * 第二个参数是缓存或数据库中符合条件总数据量
	 */
	public ResultMsgBean(T result, int length) {
		this.flag = NORMAL;
		this.result = result;
		this.length = length;
	}

	/**
	 * 构造函数：正常返回
	 * <p>
	 * 常用在INSERT、UPDATE、DELETE语句执行时
	 */
	public ResultMsgBean(int flag) {
		this.flag = flag;
		this.result = null;
	}

	/**
	 * 构造函数：程序运行时异常
	 */
	public ResultMsgBean() {
		super();
		this.flag = RUNTIMEEXCEPTION;
		this.errorCode = SYHCException.OPERATION_CODE_RUNTIME_EXCEPTION;
		this.message = "运行时异常[程序代码编译正常，运行过程产生异常]";
	}

	/**
	 * 构造函数：底层运行时出现异常情况
	 */
	public ResultMsgBean(int errorCode, String message) {
		this.flag = CATCHEXCEPTION;
		this.result = null;
		this.errorCode = errorCode;
		this.message = message;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
