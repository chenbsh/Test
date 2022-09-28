package com.coding.jdbc.exception;

/**
 * 业务异常信息定义类
 *
 * @Copyright HA
 * @Project haiot-core
 * @Author MacChen
 * @timer 2018-03-15
 * @Version 1.0.0
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
public class SYHCException extends Exception {

	private static final long serialVersionUID = -6152142510046314458L;

	/**
	 * 接口调用结果代码—成功执行
	 */
	public static final int OPERATION_CODE_NORMAL = 1000;

	/**
	 * 接口调用结果代码—鉴权失败
	 */
	public static final int OPERATION_CODE_AUTH = 1002;

	/**
	 * 接口调用结果代码—数据库执行失败
	 */
	public static final int OPERATION_CODE_SQL_ERROR = 1003;

	/**
	 * 接口调用结果代码—初始化问题
	 */
	public static final int OPERATION_CODE_INITIALIZE = 1004;

	/**
	 * 接口调用结果代码—主体被锁住/被冻结
	 */
	public static final int OPERATION_CODE_LOCK = 1005;

	/**
	 * 接口调用结果代码—参数不合格
	 */
	public static final int OPERATION_CODE_PARAMETER = 1006;

	/**
	 * 接口调用结果代码—报文格式错误
	 */
	public static final int OPERATION_CODE_FORMAT_ERROR = 1007;
	/**
	 * 接口调用结果代码—数据不存在
	 */
	public static final int OPERATION_CODE_NOTEXIST = 1008;
	/**
	 * 接口调用结果代码—已被使用
	 */
	public static final int OPERATION_CODE_EXIST = 1009;
	/**
	 * 接口调用结果代码—数据不一致
	 */
	public static final int OPERATION_CODE_NOTEQUAL = 1010;
	/**
	 * 接口调用结果代码—无法接收的报文
	 */
	public static final int OPERATION_CODE_RECEIVE = 1011;
	/**
	 * 接口调用结果代码—无应答
	 */
	public static final int OPERATION_CODE_NOFEEDBACK = 1012;
	/**
	 * 接口调用结果代码—阀值溢出
	 */
	public static final int OPERATION_CODE_THRESHOLD_OVERFLOW = 1099;

	/**
	 * 接口调用结果代码—数据库执行失败
	 */
	public static final int OPERATION_CODE_EXECUTE_FAILER = 9997;
	/**
	 * 接口调用结果代码—会话已过期
	 */
	public static final int OPERATION_CODE_TIMEOUT = 9998;
	/**
	 * 接口调用结果代码—程序运行时异常
	 */
	public static final int OPERATION_CODE_RUNTIME_EXCEPTION = 9999;
	/**
	 * 执行单条DML语句发生异常
	 */
	public static final String JDBC_EXECUTE_ONLY = "JDBC_EXECUTE_ONLY";
	/**
	 * 批量执行DML语句发生异常
	 */
	public static final String JDBC_EXECUTE_BATCH = "JDBC_EXECUTE_MORE";
	/**
	 * 执行单条查询语句发生异常
	 */
	public static final String JDBC_SELECT_ONLY = "JDBC_SELECT_ONLY";
	/**
	 * 执行查询语句预期返回一条数据实际上却包含多条数据情况
	 */
	public static final String JDBC_SELECT_RETURN_MORE = "JDBC_SELECT_RETURN_MORE";
	/**
	 * 数据库查询结果集反射成实体对象失败
	 */
	public static final String JDBC_SELECT_REFLECT_BEAN = "JDBC_SELECT_REFLECT_BEAN";

	// ----------------------------------------------------------------------------------------------------
	/**
	 * 通信结果代码
	 */
	private Integer resultCode;

	/**
	 * 通信结果描述
	 */
	private String resultMsg;
	/**
	 * 总记录数
	 */
	private Integer totalNum;
	/**
	 * 总页数
	 */
	private Integer pageNum;
	/**
	 * 当前页号：从1开始计算
	 */
	private Integer pageIndex = 1;
	/**
	 * 起始行号
	 */
	private Integer startIndex = 0;

	/**
	 * 分页记录条数大小
	 */
	private Integer pageSize = 12;

	public SYHCException() {
		super();
	}

	public SYHCException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SYHCException(String arg0) {
		super(arg0);
	}

	public SYHCException(Throwable arg0) {
		super(arg0);
	}

	public SYHCException(int code, String msg) {
		super(msg);
		this.resultCode = code;
		this.resultMsg = msg;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String toJson() {
		return "{\"resultCode\":" + this.resultCode + ",\"resultMsg\":\"" + this.getResultMsg() + "\"}";
	}

	public String toJson(String json) {
		if (this.totalNum == null && this.pageSize == null) {
			return "{\"resultCode\":" + this.resultCode + ",\"resultMsg\":\"" + this.getResultMsg() + "\",\"data\":" + json + "}";
		} else {
			return "{\"resultCode\":" + this.resultCode + ",\"resultMsg\":\"" + this.getResultMsg() + "\",\"totalNum\":" + this.totalNum + ",\"pageNum\":" + this.pageNum + ",\"pageIndex\":" + this.pageIndex + ",\"pageSize\":" + this.pageSize + ",\"data\":" + json + "}";
		}
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
		if (this.pageSize == 0 || this.totalNum == 0) {
			this.pageNum = 0;
		} else {
			this.pageNum = this.totalNum / this.pageSize;
			if (this.totalNum % this.pageSize != 0) {
				this.pageNum = this.pageNum + 1;
			}
		}
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getpageSize() {
		return pageSize;
	}

	public void setpageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartIndex() {
		if (this.pageIndex == null || this.pageSize == null) {
			this.startIndex = 0;
		} else {
			this.startIndex = (this.pageIndex - 1) * this.pageSize;
		}
		return this.startIndex;
	}

}
