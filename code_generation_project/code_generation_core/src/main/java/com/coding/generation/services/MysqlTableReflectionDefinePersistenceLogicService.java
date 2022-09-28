package com.coding.generation.services;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.coding.generation.domain.MysqlTableReflectionDefineBean;
import com.coding.jdbc.exception.SYHCException;
import com.coding.jdbc.tools.AttributeMappingTool;
import com.coding.jdbc.tools.SpringJdbcTemplateTool;

/**
 * 数据库库表生成代码配置定义信息数据库持久化类生成工具
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
public class MysqlTableReflectionDefinePersistenceLogicService {

	private Log logger = LogFactory.getLog(this.getClass().getName());

	public MysqlTableReflectionDefinePersistenceLogicService() {
		super();
	}

	@Resource(name = "basicJdbcTarget")
	private SpringJdbcTemplateTool jdbc;

	// 保存库表代码生成定义信息
	@Value("${reflection_define_insert}")
	private String reflection_define_insert;

	// 更新库表代码生成定义信息
	@Value("${reflection_define_update}")
	private String reflection_define_update;

	// 根据库表名称获取库表代码生成定义信息
	@Value("${reflection_define_queryForBean}")
	private String reflection_define_queryForBean;

	/**
	 * 添加数据库库表生成代码配置定义信息实例
	 * 
	 * @param MysqlTableReflectionDefineBean entity 数据库库表生成代码配置定义信息实例
	 * 
	 * @return Boolean true:false=成功:失败
	 * 
	 * @throws SYHCException
	 */
	public Boolean insert(MysqlTableReflectionDefineBean entity) throws SYHCException {
		String sql = null;
		try {
			sql = this.reflection_define_insert;
			sql = AttributeMappingTool.constractSql(sql, entity);
			logger.info("-------------添加数据库库表生成代码配置定义信息实例：\n" + sql);
			return jdbc.execute(sql) == 1;
		} catch (Exception e) {
			e.printStackTrace();
			String warning = "------------添加数据库库表生成代码配置定义信息实例：" + SYHCException.JDBC_EXECUTE_ONLY + "\n" + sql + "\n";
			logger.error(warning + e.getMessage());
			throw new SYHCException(warning);
		}
	}

	/**
	 * 更新数据库库表生成代码配置定义信息实例
	 * 
	 * @param MysqlTableReflectionDefineBean entity 数据库库表生成代码配置定义信息实例
	 * 
	 * @return Boolean true:false=成功:失败
	 * 
	 * @throws SYHCException
	 */
	public Boolean update(MysqlTableReflectionDefineBean entity) throws SYHCException {
		String sql = null;
		try {
			sql = this.reflection_define_update;
			sql = AttributeMappingTool.constractSql(sql, entity);
			logger.info("-------------更新数据库库表生成代码配置定义信息实例：\n" + sql);
			return jdbc.execute(sql) == 1;
		} catch (Exception e) {
			e.printStackTrace();
			String warning = "------------更新数据库库表生成代码配置定义信息实例：" + SYHCException.JDBC_EXECUTE_ONLY + "\n" + sql + "\n";
			logger.error(warning + e.getMessage());
			throw new SYHCException(warning);
		}
	}

	/**
	 * 根据库表名称获取库表代码生成定义信息
	 * 
	 * @param databaseName 数据库实例名称
	 * 
	 * @param tableName 数据库表名称
	 * 
	 * @return MysqlTableReflectionDefineBean entity 数据库库表生成代码配置定义信息实例
	 * 
	 * @throws SYHCException
	 */
	public MysqlTableReflectionDefineBean queryForBean(String databaseName, String tableName) throws SYHCException {
		String sql = null;
		try {
			sql = this.reflection_define_queryForBean;
			sql = AttributeMappingTool.constractSql(sql, new String[][] { { "databaseName", databaseName }, { "tableName", tableName } });
			logger.info("-------------根据库表名称获取库表代码生成定义信息实例：\n" + sql);
			return jdbc.queryForBean(sql, MysqlTableReflectionDefineBean.class);
		} catch (Exception e) {
			e.printStackTrace();
			String warning = "------------根据库表名称获取库表代码生成定义信息实例：" + SYHCException.JDBC_SELECT_ONLY + "\n" + sql + "\n";
			logger.error(warning + e.getMessage());
			throw new SYHCException(warning);
		}
	}
}
