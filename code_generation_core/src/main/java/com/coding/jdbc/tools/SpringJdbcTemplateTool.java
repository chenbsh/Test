package com.coding.jdbc.tools;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * 基于Spring配置数据源模式的数据库持久化事务管理接口实现类
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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SpringJdbcTemplateTool extends JdbcDaoSupport {

	/**
	 * 无参构造方法
	 */
	public SpringJdbcTemplateTool() {
		super();
	}

	/**
	 * 直接操作数据库数据
	 * 
	 * @param dmlSql Insert、Delete或Update语句
	 * @return int 执行SQL语句影响数据库记录条数
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public int execute(String dmlSql) {
		return this.getJdbcTemplate().update(dmlSql);
	}

	/**
	 * 批量直接操作数据库数据
	 * 
	 * @param dmlSql Insert、Delete或Update语句数组集合
	 * @return int[] int[i]值表示执行第i条SQL语句影响数据库记录条数
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public int[] executeBatch(String dmlSql[]) {
		return this.getJdbcTemplate().batchUpdate(dmlSql);
	}

	/**
	 * 调用数据库存储过程
	 * 
	 * @param procedure 存储过程函数名称
	 * @return void
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public void callProcedure(String procedure) {
		final String execute_procedure = "{call " + procedure + "()}";
		this.getJdbcTemplate().execute(execute_procedure, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement statement) throws SQLException, DataAccessException {
				statement.execute(execute_procedure);
				return null;
			}
		});
	}

	/**
	 * 执行DDL-SQL检索语句获取单条数据记录并封装成指定格式的JavaBean对象
	 * 
	 * @param ddlSql SQL检索语句
	 * @param beanClass JavaBean.Class
	 * @return Bean 封装成制定格式的JavaBean对象
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public <T> T queryForBean(String ddlSql, Class<T> beanClass) {
		try {
			return this.getJdbcTemplate().queryForObject(ddlSql, new BeanPropertyRowMapper<T>(beanClass));
		} catch (EmptyResultDataAccessException e) {
			logger.error("------queryForBean----根据SQL语句查询单条数据记录时，数据库不存在数据----------\n" + ddlSql + "\n\n");
		} catch (IncorrectResultSizeDataAccessException e) {
			logger.error("------queryForBean----根据SQL语句查询单条数据记录时，数据库返回记录超过一条----------\n" + ddlSql + "\n\n");
			throw new RuntimeException("根据SQL语句查询单条数据记录时，数据库返回记录超过一条");
		}
		return null;
	}

	/**
	 * 执行DDL-SQL检索语句获取数据记录集合并根据指定格式的JavaBean类型封装成List集合
	 * 
	 * @param ddlSql SQL检索语句
	 * @param beanClass JavaBean.Class
	 * @return List<Bean> 指定JavaBean实例集合
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public <T> List<T> queryForList(String ddlSql, Class<T> beanClass) {
		return this.getJdbcTemplate().query(ddlSql, new BeanPropertyRowMapper<T>(beanClass));
	}

	/**
	 * 执行DDL-SQL检索语句获取一个短整
	 * 
	 * @param ddlSql SQL检索语句
	 * @return Integer 短整数值
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public Integer queryForInteger(String ddlSql) {
		try {
			return getJdbcTemplate().queryForObject(ddlSql, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			logger.error("-----queryForInteger-----根据SQL语句查询单条数据记录时，数据库不存在数据----------\n" + ddlSql + "\n\n");
		} catch (IncorrectResultSizeDataAccessException e) {
			logger.error("-----queryForInteger-----根据SQL语句查询单条数据记录时，数据库返回记录超过一条----------\n" + ddlSql + "\n\n");
			throw new RuntimeException("根据SQL语句查询单条数据记录时，数据库返回记录超过一条");
		}
		return 0;
	}

	/**
	 * 执行SQL检索语句获取一个长整
	 * 
	 * @param ddlSql SQL检索语句
	 * @return Long 长整数值
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public Long queryForLong(String ddlSql) {
		try {
			return getJdbcTemplate().queryForObject(ddlSql, Long.class);
		} catch (EmptyResultDataAccessException e) {
			logger.error("-----queryForLong-----根据SQL语句查询单条数据记录时，数据库不存在数据----------\n" + ddlSql + "\n\n");
		} catch (IncorrectResultSizeDataAccessException e) {
			logger.error("-----queryForLong-----根据SQL语句查询单条数据记录时，数据库返回记录超过一条----------\n" + ddlSql + "\n\n");
			throw new RuntimeException("根据SQL语句查询单条数据记录时，数据库返回记录超过一条");
		}
		return 0L;
	}

	/**
	 * 执行SQL检索语句获取一个长整
	 * 
	 * @param ddlSql SQL检索语句
	 * @return String 字符串数值
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public String queryForString(String ddlSql) {
		try {
			return getJdbcTemplate().queryForObject(ddlSql, String.class);
		} catch (EmptyResultDataAccessException e) {
			logger.error("-----queryForLong-----根据SQL语句查询单条数据记录时，数据库不存在数据----------\n" + ddlSql + "\n\n");
		} catch (IncorrectResultSizeDataAccessException e) {
			logger.error("-----queryForLong-----根据SQL语句查询单条数据记录时，数据库返回记录超过一条----------\n" + ddlSql + "\n\n");
			throw new RuntimeException("根据SQL语句查询单条数据记录时，数据库返回记录超过一条");
		}
		return null;
	}

	/**
	 * 执行SQL检索语句获取字符串列表
	 * 
	 * @param ddlSql SQL检索语句
	 * @return List<String> 字符串集合
	 * @Author MacChen
	 * @time 2017-12-01
	 */
	public List<String> queryForListString(String ddlSql) {
		SqlRowSet rs = this.getJdbcTemplate().queryForRowSet(ddlSql);
		List<String> list = new ArrayList<String>();
		while (rs.next()) {
			list.add(rs.getString(1));
		}
		return list;
	}

}
