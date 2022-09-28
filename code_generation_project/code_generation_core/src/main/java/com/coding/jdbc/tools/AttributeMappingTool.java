package com.coding.jdbc.tools;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.coding.jdbc.domain.BasicBean;
import com.coding.jdbc.domain.JdbcParameterBean;
import com.coding.jdbc.exception.SYHCException;

/**
 * 数据库持久化事务管理接口实现类
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
public class AttributeMappingTool {

	public AttributeMappingTool() {
		super();
	}

	/**
	 * 构建数据库持久化SQL语句
	 * 
	 * @param source 源SQL语句
	 * @param paramName 参数名称
	 * @param paramValue 参数值
	 * @return String target 目标SQL语句
	 * @throws SYHCException
	 * @Author MacChen
	 * @time 2018-03-28
	 */
	public static String constractSql(String source, String paramName, String paramValue) throws SYHCException {
		if (StringUtils.isBlank(source)) {
			throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "SQL statement template is empty");
		}
		if (StringUtils.isBlank(paramName)) {
			throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "Property parameter name is empty");
		}
		paramName = "#" + paramName + "#";
		// 过滤SQL注入
		if (paramName.toLowerCase().equals("#condition#") == false && (AttributeMappingTool.isInjectSqlParameter(paramValue))) {
			source = "［WARNING：SQL INTO］" + source + "\nparameter=" + paramName + "\tvalue=" + paramValue;
			throw new SYHCException(SYHCException.OPERATION_CODE_FORMAT_ERROR, source);
		}
		// 检测是否存在参数
		if (source.contains(paramName) == false) {
			throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "property 【" + paramName + "】 is not exists in the SQL");
		}
		while (source.contains(" ,")) {
			source = source.replaceAll(" ,", ",");
		}
		while (source.contains(", ")) {
			source = source.replaceAll(", ", ",");
		}
		if (StringUtils.isNotBlank(paramValue)) {
			// 特殊符号处理
			paramValue = paramValue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
			// 参数值带英文单引号时问题【忽略参数：condition】
			if (paramName.toLowerCase().equals("#condition#") == false) {
				paramValue = paramValue.replaceAll("'", "''");
			}
			// replaceAll 用正则表达式，里面包括 [ \ ^ $ . | ? * + ( ) { } 符号时会出现问题。改成replace方式
			while (source.contains(paramName)) {
				source = source.replace(paramName, paramValue);
			}
		} else {
			source = source.replaceAll(paramName, "");
		}

		while (source.contains(" ,")) {
			source = source.replaceAll("' ", "'");
		}
		while (source.contains(" '")) {
			source = source.replaceAll(" '", "'");
		}
		while (source.contains(",,")) {
			source = source.replace(",,", ",null,");
		}
		while (source.contains(",'null',")) {
			source = source.replace(",'null',", ",null,");
		}
		while (source.contains(",'',")) {
			source = source.replace(",'',", ",null,");
		}
		while (source.contains("=,")) {
			source = source.replace(",=", "=null,");
		}
		while (source.contains("=''")) {
			source = source.replace("=''", "=null");
		}
		while (source.contains("\"null\"")) {
			source = source.replace("\"null\"", "\"\"");
		}
		while (source.contains("str_to_date('','%Y-%m-%d')")) {
			source = source.replace("str_to_date('','%Y-%m-%d')", "null");
		}
		while (source.contains("str_to_date('','%Y-%m-%d %H:%i:%s')")) {
			source = source.replace("str_to_date('','%Y-%m-%d %H:%i:%s')", "null");
		}
		return source;
	}

	/**
	 * 构建数据库持久化SQL语句
	 * 
	 * @param source 源SQL语句
	 * @param parameterArray 二维数组，其中params[i][0]是参数名称；paramspi][1]是参数值
	 * @return String target 目标SQL语句
	 * @throws SYHCException
	 * @Author MacChen
	 * @time 2018-03-28
	 */
	public static String constractSql(String source, String parameterArray[][]) throws SYHCException {
		if (StringUtils.isBlank(source)) {
			throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "SQL statement template is empty");
		}
		if (parameterArray == null || parameterArray.length == 0) {
			throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "Property parameter array is empty");
		}
		while (source.contains(" ,")) {
			source = source.replaceAll(" ,", ",");
		}
		while (source.contains(", ")) {
			source = source.replaceAll(", ", ",");
		}
		for (String parameter[] : parameterArray) {
			parameter[0] = "#" + parameter[0] + "#";
			// 过滤SQL注入
			if (parameter[0].toLowerCase().equals("#condition#") == false && (AttributeMappingTool.isInjectSqlParameter(parameter[1]))) {
				source = "［WARNING：SQL INTO］" + source + "\nparameter=" + parameter[0] + "\tvalue=" + parameter[1];
				throw new SYHCException(SYHCException.OPERATION_CODE_FORMAT_ERROR, source);
			}
			// 检测是否存在参数
			if (source.contains(parameter[0]) == false) {
				throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "property 【" + parameter[0] + "】 is not exists in the SQL");
			}
			// 特殊符号处理
			if (StringUtils.isNotBlank(parameter[1])) {
				parameter[1] = parameter[1].replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
				// 参数值带英文单引号时问题【忽略参数：condition】
				if (parameter[0].toLowerCase().equals("#condition#") == false) {
					parameter[1] = parameter[1].replaceAll("'", "''");
				}
			}
		}
		while (source.contains(" ,")) {
			source = source.replaceAll(" ,", ",");
		}
		while (source.contains(", ")) {
			source = source.replaceAll(", ", ",");
		}
		for (String parameter[] : parameterArray) {
			if (StringUtils.isNotBlank(parameter[1])) {
				while (source.contains(parameter[0])) {
					source = source.replace(parameter[0], parameter[1]);
				}
			} else {
				source = source.replaceAll(parameter[0], "");
			}
		}
		parameterArray = null;
		while (source.contains(" ,")) {
			source = source.replaceAll("' ", "'");
		}
		while (source.contains(" '")) {
			source = source.replaceAll(" '", "'");
		}
		while (source.contains(",,")) {
			source = source.replace(",,", ",null,");
		}
		while (source.contains(",'null',")) {
			source = source.replace(",'null',", ",null,");
		}
		while (source.contains(",'',")) {
			source = source.replace(",'',", ",null,");
		}
		while (source.contains("=,")) {
			source = source.replace(",=", "=null,");
		}
		while (source.contains("=''")) {
			source = source.replace("=''", "=null");
		}
		while (source.contains("\"null\"")) {
			source = source.replace("\"null\"", "\"\"");
		}
		while (source.contains("str_to_date('','%Y-%m-%d')")) {
			source = source.replace("str_to_date('','%Y-%m-%d')", "null");
		}
		while (source.contains("str_to_date('','%Y-%m-%d %H:%i:%s')")) {
			source = source.replace("str_to_date('','%Y-%m-%d %H:%i:%s')", "null");
		}
		return source;
	}

	/**
	 * 构建数据库持久化SQL语句
	 * 
	 * @param source 源SQL语句
	 * @param javaBean JavaBean数据存储对象
	 * @return String target 目标SQL语句
	 * @throws SYHCException
	 * @Author MacChen
	 * @time 2018-03-28
	 */
	public static String constractSql(String source, BasicBean javaBean) throws SYHCException {
		if (StringUtils.isBlank(source)) {
			throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "SQL statement template is empty");
		}
		if (javaBean == null) {
			throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "Property parameter JavaBean instance is empty");
		}

		while (source.contains(" ,")) {
			source = source.replaceAll(" ,", ",");
		}
		while (source.contains(", ")) {
			source = source.replaceAll(", ", ",");
		}
		Field[] field = javaBean.getClass().getDeclaredFields();// 获取JavaBean所有属性变量
		String paramName = null; // 属性变量名称
		String paramValue = null; // 属性变量数值
		// 构建ToString方法返回字符串值
		for (int i = 0; i < field.length; i++) {
			if (field[i].getName().equals("serialVersionUID")) {
				continue;
			}
			// 单一安全性检查
			field[i].setAccessible(true);
			paramName = "#" + field[i].getName() + "#";
			// 检测是否存在参数
			if (source.contains(paramName) == false) {
				continue;
			}
			try {
				paramValue = field[i].get(javaBean).toString();
			} catch (IllegalAccessException e) {
				throw new SYHCException(SYHCException.OPERATION_CODE_NOTEXIST, "Cannot get the value of the attribute parameter【" + field[i].getName() + "】");
			} catch (NullPointerException e) {
				paramValue = null;
			}
			// 过滤SQL注入
			if (paramName.toLowerCase().equals("#condition#") == false && (AttributeMappingTool.isInjectSqlParameter(paramValue))) {
				source = "［WARNING：SQL INTO］" + source + "\nparameter=" + paramName + "\tvalue=" + paramValue;
				throw new SYHCException(SYHCException.OPERATION_CODE_FORMAT_ERROR, source);
			}
			// 特殊符号处理
			if (StringUtils.isNotBlank(paramValue)) {
				paramValue = paramValue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
				// 参数值带英文单引号时问题【忽略参数：condition】
				if (paramName.toLowerCase().equals("#condition#") == false) {
					paramValue = paramValue.replaceAll("'", "''");
				}
			}
			if (StringUtils.isNotBlank(paramValue)) {
				// replaceAll 用正则表达式，里面包括 [ \ ^ $ . | ? * + ( ) { } 符号时会出现问题。改成replace方式
				while (source.contains(paramName)) {
					source = source.replace(paramName, paramValue);
				}
			} else {
				source = source.replaceAll(paramName, "");
			}
		}
		while (source.contains(" ,")) {
			source = source.replaceAll("' ", "'");
		}
		while (source.contains(" '")) {
			source = source.replaceAll(" '", "'");
		}
		while (source.contains(",,")) {
			source = source.replace(",,", ",null,");
		}
		while (source.contains(",'null',")) {
			source = source.replace(",'null',", ",null,");
		}
		while (source.contains(",'',")) {
			source = source.replace(",'',", ",null,");
		}
		while (source.contains("=,")) {
			source = source.replace(",=", "=null,");
		}
		while (source.contains("=''")) {
			source = source.replace("=''", "=null");
		}
		while (source.contains("\"null\"")) {
			source = source.replace("\"null\"", "\"\"");
		}
		while (source.contains("str_to_date('','%Y-%m-%d')")) {
			source = source.replace("str_to_date('','%Y-%m-%d')", "null");
		}
		while (source.contains("str_to_date('','%Y-%m-%d %H:%i:%s')")) {
			source = source.replace("str_to_date('','%Y-%m-%d %H:%i:%s')", "null");
		}
		return source;
	}

	/**
	 * 根据动态条件构建数据库查询WHERE条件<br/>
	 * <p>
	 * 支持模式例如：1. a and b and c 2. (a and b) or (a and c) 3. a or b or c
	 * <p>
	 * 不支持模式例如：(a and (b or c) and d
	 * 
	 * @param paramList 动态条件
	 * @param orderBy 二维数组，排序属性键值对 array[i][0]=属性名称， array[i][1]=排序方式［asc,desc］
	 * @return String 数据库查询WHERE条件字符串
	 * @throws SYHCException
	 * @Author MacChen
	 * @time 2018-03-28
	 */
	public static String constractWhere(List<JdbcParameterBean> paramList, String orderBy[][]) throws SYHCException {
		StringBuilder where = null;
		if ((paramList == null || paramList.isEmpty() || paramList.size() == 0) == false) {
			JdbcParameterBean parameter = null;
			where = new StringBuilder(" where ");
			Iterator<JdbcParameterBean> it = paramList.iterator();
			while (it.hasNext()) {
				parameter = it.next();
				// 过滤SQL注入
				if (AttributeMappingTool.isInjectSqlParameter(parameter.getValue())) {
					System.out.println("［WARNING：SQL INTO］parameter=" + parameter.getName() + "\tvalue=" + parameter.getValue());
					throw new SYHCException("［WARNING：SQL INTO］parameter=" + parameter.getName() + "\tvalue=" + parameter.getValue());
				}
				// 前括弧
				if (parameter.getStartBracket() == 1) {
					where.append(" (");
				} else if (parameter.getStartBracket() > 1) {
					where.append(" (");
					for (int i = 1; i < parameter.getStartBracket(); i++) {
						where.append("(");
					}
				}
				// 参数条件
				where.append(parameter.toString());
				// 后括弧
				if (parameter.getEndBracket() == 1) {
					where.append(") ");
				} else if (parameter.getEndBracket() > 1) {
					for (int i = 1; i < parameter.getEndBracket(); i++) {
						where.append(")");
					}
					where.append(") ");
				}
				// 与下一个参数关系
				if (it.hasNext()) {
					where.append(" " + parameter.getRelation() + " ");
				}
			}
		}
		// 排序
		if (orderBy != null) {
			if (where == null) {
				where = new StringBuilder(" order by ");
			} else {
				where.append(" order by ");
			}
			for (int i = 0; i < orderBy.length; i++) {
				where.append(orderBy[i][0] + " " + orderBy[i][1]);
				if ((i + 1) < orderBy.length) {
					where.append(",");
				}
			}
		}
		return where == null ? "" : where.toString();
	}

	/**
	 * @param paramList 动态条件 构造JdbcParameterBean时请使用 String name, String value, int typecode 的构造函数创建set语句
	 * @return 数据库更新SET条件字符串
	 * @throws SYHCException
	 */
	public static String constractUpdateField(List<JdbcParameterBean> paramList) throws SYHCException {
		if (paramList == null || paramList.isEmpty() || paramList.size() == 0) {
			return null;
		}
		JdbcParameterBean parameter = null;
		StringBuilder updateSet = new StringBuilder(" set ");
		for (int i = 0; i < paramList.size(); i++) {
			parameter = paramList.get(i);
			parameter.setOperator(JdbcParameterBean.EQUALS);
			// 过滤SQL注入
			if (AttributeMappingTool.isInjectSqlParameter(parameter.getValue())) {
				System.out.println("［WARNING：SQL INTO］parameter=" + parameter.getName() + "\tvalue=" + parameter.getValue());
				throw new SYHCException("［WARNING：SQL INTO］parameter=" + parameter.getName() + "\tvalue=" + parameter.getValue());
			}
			if (i == 0) {
				updateSet.append(parameter.toString());
			} else {
				updateSet.append("," + parameter.toString());
			}
		}

		return updateSet.toString();
	}

	/**
	 * 判断字符串参数是否存在SQL注入风险
	 * 
	 * @param parameter 要验证的字符串参数
	 * @return true:falsg=存在:不存在
	 * @Author MacChen
	 * @time 2018-03-28
	 */
	private static Boolean isInjectSqlParameter(String parameter) {
		if (StringUtils.isEmpty(parameter)) {
			return false;
		}
		parameter = parameter.toUpperCase();
		if (parameter.indexOf(" OR ") != -1 || parameter.indexOf(" AND ") != -1 || parameter.indexOf("'OR") != -1 || parameter.indexOf("'AND") != -1 || parameter.indexOf("AND(") != -1 || parameter.indexOf("OR(") != -1) {
			return true;
		}
		return false;
	}
}
