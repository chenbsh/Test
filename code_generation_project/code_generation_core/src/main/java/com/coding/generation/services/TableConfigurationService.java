package com.coding.generation.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.coding.generation.domain.TableColumnBean;
import com.coding.generation.domain.TableCommentBean;
import com.coding.jdbc.exception.SYHCException;

/**
 * 数据库表结构信息查询持久化接口
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
@Repository
public class TableConfigurationService {

	private Log logger = LogFactory.getLog(this.getClass().getName());

	// MYSQL数据库JDBC_URL地址
	@Value("${basic_jdbc_url}")
	private String basic_jdbc_url;

	// MYSQL数据库JDBC驱动
	@Value("${basic_database}")
	private String basic_database;

	// MYSQL数据库JDBC驱动
	@Value("${basic_driver}")
	private String basic_driver;

	// MYSQL数据库帐号用户名称
	@Value("${basic_username}")
	private String basic_username;

	// MYSQL数据库帐号用户密码
	@Value("${basic_password}")
	private String basic_password;

	/**
	 * 根据表名称获取表结构基本信息
	 * 
	 * @param tableName 库表名称
	 * 
	 * @return List<TableColumnBean> columnList 列属性定义信息实例集合
	 * 
	 * @throws SYHCException
	 */
	public List<TableColumnBean> queryForColumnList(String tableName) throws SYHCException {
		Connection connection = null;
		TableColumnBean column = null;
		List<TableColumnBean> columnList = null;
		System.out.println("tableName = " + tableName);
		try {
			// 连接数据库
			Class.forName(basic_driver);
			// 建立数据库连接
			connection = DriverManager.getConnection(basic_jdbc_url, basic_username, basic_password);
			Statement statement = connection.createStatement();
			ResultSet columnSet = statement.executeQuery("select table_schema,table_name,column_name,data_type,column_comment from information_schema.columns where table_schema='" + basic_database + "' and table_name='" + tableName + "'");

			Set<String> columnNameSet = new HashSet<String>();
			while (columnSet.next()) {
				column = new TableColumnBean();
				column.setDatabaseName(columnSet.getString("TABLE_SCHEMA"));
				column.setTableName(columnSet.getString("TABLE_NAME"));
				column.setColumnName(columnSet.getString("COLUMN_NAME"));
				column.setColumnType(columnSet.getString("DATA_TYPE"));
				column.setColumnComment(columnSet.getString("COLUMN_COMMENT"));
				if (columnNameSet.contains(column.getColumnName()) == false) {
					if (columnList == null) {
						columnList = new ArrayList<TableColumnBean>();
					}
					columnList.add(column);
					columnNameSet.add(column.getColumnName());
				}
			}
			columnNameSet.clear();
			columnSet.close();
			connection.close();
			logger.info("成功获取表【" + tableName + "】结构");
			// System.out.println(JSON.toJSONString(columnList));
			return columnList;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		throw new SYHCException(SYHCException.OPERATION_CODE_INITIALIZE, "初始化数据库连接异常");
	}

	/**
	 * 根据表名称获取表结构基本信息
	 * 
	 * @param tableName 库表名称
	 * 
	 * @return Map<String,TableColumnBean> columnMap 列属性定义信息实例集合
	 * 
	 * @throws SYHCException
	 */
	public Map<String, TableColumnBean> queryForColumnMap(String tableName) throws SYHCException {
		Connection connection = null;
		TableColumnBean column = null;
		Map<String, TableColumnBean> columnMap = null;
		try {
			// 连接数据库
			Class.forName(basic_driver);
			// 建立数据库连接
			connection = DriverManager.getConnection(basic_jdbc_url, basic_username, basic_password);
			Statement statement = connection.createStatement();
			ResultSet columnSet = statement.executeQuery("select table_schema,table_name,column_name,data_type,column_comment from information_schema.columns where table_schema='" + basic_database + "' and table_name='" + tableName + "'");
			while (columnSet.next()) {
				column = new TableColumnBean();
				column.setDatabaseName(columnSet.getString("TABLE_SCHEMA"));
				column.setTableName(columnSet.getString("TABLE_NAME"));
				column.setColumnName(columnSet.getString("COLUMN_NAME"));
				column.setColumnType(columnSet.getString("DATA_TYPE"));
				column.setColumnComment(columnSet.getString("COLUMN_COMMENT"));
				if (columnMap == null) {
					columnMap = new LinkedHashMap<String, TableColumnBean>();
				}
				columnMap.put(column.getColumnName(), column);
			}
			columnSet.close();
			connection.close();
			logger.info("成功获取表【" + tableName + "】结构");
			return columnMap;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		throw new SYHCException(SYHCException.OPERATION_CODE_INITIALIZE, "初始化数据库连接异常");
	}

	/**
	 * 获取数据库表信息列表
	 * 
	 * @return List<TableCommentBean > tableList 数据库表信息实例集合
	 * 
	 * @throws SYHCException
	 */
	public List<TableCommentBean> queryForTableList() throws SYHCException {
		Connection connection = null;
		TableCommentBean table = null;
		List<TableCommentBean> tableList = null;
		try {
			// 连接数据库
			Class.forName(basic_driver);
			// 建立数据库连接
			connection = DriverManager.getConnection(basic_jdbc_url, basic_username, basic_password);
			Statement statement = connection.createStatement();
			ResultSet columnSet = statement.executeQuery("select table_schema,table_comment,table_name,table_collation,create_time from information_schema.tables where table_schema='" + basic_database + "' and table_name!='mysql_table_reflection_define' order by table_name asc");
			while (columnSet.next()) {
				table = new TableCommentBean();
				table.setDatabaseName(columnSet.getString("TABLE_SCHEMA"));
				table.setTableName(columnSet.getString("TABLE_NAME"));
				table.setTableComment(columnSet.getString("TABLE_COMMENT"));
				table.setTableCollation(columnSet.getString("TABLE_COLLATION"));
				table.setCreateTime(columnSet.getString("CREATE_TIME"));
				if (tableList == null) {
					tableList = new ArrayList<TableCommentBean>();
				}
				tableList.add(table);
			}
			columnSet.close();
			connection.close();
			logger.info("成功获取数据库表信息列表【" + basic_database + "】");
			return tableList;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		throw new SYHCException(SYHCException.OPERATION_CODE_INITIALIZE, "初始化数据库连接异常");
	}

}
