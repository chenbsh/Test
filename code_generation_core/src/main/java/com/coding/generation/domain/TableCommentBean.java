package com.coding.generation.domain;

import com.coding.jdbc.domain.BasicBean;

/**
 * 数据库表基本信息Bean
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
public class TableCommentBean extends BasicBean {

	private static final long serialVersionUID = -4627960320684832978L;

	public TableCommentBean() {
		super();
	}

	/** 数据库实例名称 */
	private String databaseName;

	/** 数据库表名称 */
	private String tableName;

	/** 数据库表注解 */
	private String tableComment;

	/** 字符集排序方式 */
	private String tableCollation;

	/** 创建时间 */
	private String createTime;

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

	public String getTableCollation() {
		return tableCollation;
	}

	public void setTableCollation(String tableCollation) {
		this.tableCollation = tableCollation;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
