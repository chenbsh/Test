package com.coding.generation.domain;

import com.coding.jdbc.domain.BasicBean;

/**
 * 数据库库表生成代码配置定义信息Bean
 * 
 * @Copyright MacChen
 * @Project CodeGenerationTool
 * @Author MacChen
 * @timer 2017-12-01
 * @Version 1.0.0
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
public class MysqlTableReflectionDefineBean extends BasicBean {

	private static final long serialVersionUID = -4627960320684832978L;

	public MysqlTableReflectionDefineBean() {
		super();
	}

	/** 数据库实例名称 */
	private String databaseName;

	/** 数据库表名称 */
	private String tableName;

	/** 数据库表注解 */
	private String tableComment;

	/** 代码包头名称 */
	private String packageName;

	/** 列规则定义JSON */
	private String columnListJson;

	/** 方法规则定义JSON */
	private String methodListJson;

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getColumnListJson() {
		return columnListJson;
	}

	public void setColumnListJson(String columnListJson) {
		this.columnListJson = columnListJson;
	}

	public String getMethodListJson() {
		return methodListJson;
	}

	public void setMethodListJson(String methodListJson) {
		this.methodListJson = methodListJson;
	}

}
