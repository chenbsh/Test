package com.coding.generation.services;

import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.coding.generation.domain.TableColumnBean;

/**
 * 数据库持久化类生成工具
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
public class CreateJdbcLogicService {

	// 版权所有人
	@Value("${coding_copyright}")
	private String coding_copyright;

	// 工程项目
	@Value("${coding_project}")
	private String coding_project;

	// 代码编写工程师
	@Value("${coding_author}")
	private String coding_author;

	// JDK版本
	@Value("${coding_JDK_version}")
	private String coding_JDK_version;

	public String persistence(String tableName, String tableComment, String packageName, String codingHead, Collection<TableColumnBean> columnList, String defaultMethod[]) {
		Boolean conditionFlag = false;
		Boolean statisticsFlag = false;

		StringBuilder persistence = new StringBuilder("package " + packageName + ".jdbc;\n\n");
		persistence.append("import " + packageName + ".domain." + codingHead + "Bean;\n");

		persistence.append("import com.syhc.jdbc.domain.JdbcParameterBean;\n");
		persistence.append("import com.syhc.jdbc.exception.SYHCIoTException;\n");
		persistence.append("import com.syhc.jdbc.tools.AttributeMappingTool;\n");
		persistence.append("import com.syhc.common.utils.GeneralUtilTools;\n");
		persistence.append("import com.syhc.jdbc.tools.SpringJdbcTemplateTool;\n");
		persistence.append("import com.syhc.common.constant.SYHCIoTExceptionEnum;\n");
		persistence.append("import org.springframework.beans.factory.annotation.Value;\n");
		persistence.append("import org.springframework.context.annotation.PropertySource;\n");
		persistence.append("import org.springframework.stereotype.Repository;\n");
		persistence.append("import javax.annotation.Resource;\n");
		persistence.append("import java.util.List;\n\n");
		persistence.append("import org.slf4j.Logger;\n");
		persistence.append("import org.slf4j.LoggerFactory;\n");

		persistence.append("/**\n");
		persistence.append(tableComment + "信息业务逻辑持久化类\n");
		persistence.append("*\n");
		persistence.append("* @Copyright " + this.coding_copyright + "\n");
		persistence.append("* @Project " + this.coding_project + "\n");
		persistence.append("* @Author " + this.coding_author + "\n");
		persistence.append("* @timer " + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd") + "\n");
		persistence.append("* @Version 1.0.0\n");
		persistence.append("* @DK version used " + this.coding_JDK_version + "\n");
		persistence.append("* @Modification history none\n");
		persistence.append("* @Modified by none\n");
		persistence.append("*/\n\n");

		String array[] = packageName.split("\\.");
		persistence.append("@Repository\n");
		persistence.append("@PropertySource(\"classpath:system_" + array[array.length - 1] + "_sql.properties\")\n");
		persistence.append("public class " + codingHead + "JdbcService {\n\n");

		persistence.append("public " + codingHead + "JdbcService() {\n");
		persistence.append("super();\n");
		persistence.append("}\n\n");

		persistence.append("private final Logger logger = LoggerFactory.getLogger(this.getClass());\n\n");

		if (tableName.startsWith("tb_log_")) {
			persistence.append("@Resource(name = \"loggerJdbcTarget\")\n");
		} else {
			persistence.append("@Resource(name = \"basicJdbcTarget\")\n");
		}
		persistence.append("private SpringJdbcTemplateTool jdbc;\n\n");

		String propertyKey = this.propertyHead(tableName);
		String columnName = null;
		String propertySQL = null;
		// 生成insert、update对应SQL语句设值注入
		if (defaultMethod != null) {
			for (String method : defaultMethod) {
				if (method.equals("insert")) {
					propertySQL = propertyKey + "_insert";
					persistence.append("//新增" + tableComment + "信息实例\n");
					persistence.append("@Value(\"${" + propertySQL + "}\")\n");
					persistence.append("private String " + propertySQL + ";\n");
					persistence.append("\n\n");
				} else if (method.equals("update")) {
					propertySQL = propertyKey + "_update";
					persistence.append("//更新" + tableComment + "信息实例\n");
					persistence.append("@Value(\"${" + propertySQL + "}\")\n");
					persistence.append("private String " + propertySQL + ";\n");
					persistence.append("\n\n");
				} else if (method.equals("updateDynamicField")) {
					propertySQL = propertyKey + "_updateDynamicField";
					persistence.append("//动态更新" + tableComment + "列字段值\n");
					persistence.append("@Value(\"${" + propertySQL + "}\")\n");
					persistence.append("private String " + propertySQL + ";\n");
					persistence.append("\n\n");
				} else if (method.equals("condition")) {
					conditionFlag = true;
				} else if (method.equals("statistics")) {
					statisticsFlag = true;
				}
			}
		}
		// 生成delete对应SQL语句设值注入
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getRemoveMothod())) {
				columnName = column.formatJava();
				propertySQL = propertyKey + "_deleteBy" + columnName;
				persistence.append("//根据" + column.getColumnComment() + "删除" + tableComment + "信息实例\n");
				persistence.append("@Value(\"${" + propertySQL + "}\")\n");
				persistence.append("private String " + propertySQL + ";\n");
				persistence.append("\n\n");
			}
		}
		// 生成queryForBean对应SQL语句设值注入
		persistence.append("//获取" + tableComment + "信息实例【基础查询语句】\n");
		persistence.append("@Value(\"${" + propertyKey + "_queryForBean}\")\n");
		persistence.append("private String " + propertyKey + "_queryForBean;\n");
		persistence.append("\n\n");

		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getBeanMothod())) {
				columnName = column.formatJava();
				propertySQL = propertyKey + "_queryForBeanBy" + columnName;
				persistence.append("//根据" + column.getColumnComment() + "获取" + tableComment + "信息实例\n");
				persistence.append("@Value(\"${" + propertySQL + "}\")\n");
				persistence.append("private String " + propertySQL + ";\n");
				persistence.append("\n\n");
			}
		}
		// 生成queryForList对应SQL语句设值注入
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getListMothod())) {
				columnName = column.formatJava();
				propertySQL = propertyKey + "_queryForListBy" + columnName;
				persistence.append("//根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合\n");
				persistence.append("@Value(\"${" + propertySQL + "}\")\n");
				persistence.append("private String " + propertySQL + ";\n");
				persistence.append("\n\n");
			}
		}
		if (conditionFlag) {
			propertySQL = propertyKey + "_queryForListByCondition";
			persistence.append("//根据动态查询条件获取" + tableComment + "信息实例集合\n");
			persistence.append("@Value(\"${" + propertySQL + "}\")\n");
			persistence.append("private String " + propertySQL + ";\n");
			persistence.append("\n\n");
		}
		if (statisticsFlag) {
			propertySQL = propertyKey + "_statisticsForListByCondition";
			persistence.append("//根据动态查询条件统计" + tableComment + "信息实例记录条数\n");
			persistence.append("@Value(\"${" + propertySQL + "}\")\n");
			persistence.append("private String " + propertySQL + ";\n");
			persistence.append("\n\n");
		}

		if (defaultMethod != null) {
			for (String method : defaultMethod) {
				if (method.equals("insert")) {
					persistence.append(this.persistenceInsert(tableComment, codingHead, propertyKey));
					persistence.append("\n\n");
				} else if (method.equals("insertBatch")) {
					persistence.append(this.persistenceInsertBatch(tableComment, codingHead, propertyKey));
					persistence.append("\n\n");
				} else if (method.equals("update")) {
					persistence.append(this.persistenceUpdate(tableComment, codingHead, propertyKey));
					persistence.append("\n\n");
				} else if (method.equals("updateDynamicField")) {
					persistence.append(this.persistenceUpdateDynamicField(tableComment, codingHead, propertyKey, columnList));
					persistence.append("\n\n");
				}
			}
		}

		// 生成delete方法
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getRemoveMothod())) {
				persistence.append(this.persistenceDelete(tableComment, codingHead, propertyKey, column));
				persistence.append("\n\n");
			}
		}
		// 生成queryForBean方法
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getBeanMothod())) {
				persistence.append(this.persistenceBean(tableComment, codingHead, propertyKey, column));
				persistence.append("\n\n");
			}
		}
		// 生成queryForList方法
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getListMothod())) {
				persistence.append(this.persistenceList(tableComment, codingHead, propertyKey, column));
				persistence.append("\n\n");
			}
		}

		if (conditionFlag) {
			persistence.append(this.persistenceCondition(tableComment, codingHead, propertyKey));
			persistence.append("\n\n");
		}
		if (statisticsFlag) {
			persistence.append(this.persistenceStatistics(tableComment, codingHead, propertyKey));
			persistence.append("\n\n");
		}
		persistence.append("}");
		return persistence.toString();
	}

	private String propertyHead(String tableName) {
		return tableName.replaceFirst("tb_", "").replaceFirst("v_", "").replaceFirst("mv_", "");
	}

	private String persistenceInsert(String tableComment, String codingHead, String propertyKey) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("新增" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("@param entity " + tableComment + "信息实例\n");
		insert.append("@return Boolean true:false=成功:失败\n");
		insert.append("@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public Boolean insert(" + codingHead + "Bean entity) throws SYHCIoTException {\n");
		insert.append("String sql = null;\n");
		insert.append("try {\n");
		insert.append("sql = this." + propertyKey + "_insert;\n");
		insert.append("sql = AttributeMappingTool.constractSql(sql, entity);\n");
		insert.append("logger.info(\"-------------新增" + tableComment + "信息实例：\\n\" + sql);\n");
		insert.append("return jdbc.execute(sql) == 1;\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------新增" + tableComment + "信息实例：\" + SYHCIoTExceptionEnum.JDBC_EXECUTE_ONLY.getCode()  + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"新增" + tableComment + "信息实例SQL错误\");\n");
		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String persistenceInsertBatch(String tableComment, String codingHead, String propertyKey) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("批量新增" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("*@param entityList " + tableComment + "信息实例集合\n");
		insert.append("*@return Boolean true:false=成功:失败\n");
		insert.append("*@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public Boolean insert(List <" + codingHead + "Bean> entityList) throws SYHCIoTException {\n");
		insert.append("String sql[] = new String[entityList.size()];\n");
		insert.append("try {\n");
		insert.append("int index = 0;\n");
		insert.append("for (" + codingHead + "Bean entity : entityList) {\n");
		insert.append("sql[index] = this." + propertyKey + "_insert;\n");
		insert.append("sql[index] = AttributeMappingTool.constractSql(sql[index], entity);\n");
		insert.append("index = index + 1;\n");
		insert.append("}\n");
		insert.append("logger.info(\"-------------批量新增" + tableComment + "信息实例：\\n\" + GeneralUtilTools.mergeString(sql, \"\\n\"));\n");
		insert.append("return jdbc.executeBatch(sql)[0] == 1;\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------批量新增" + tableComment + "信息实例：\" + SYHCIoTExceptionEnum.JDBC_EXECUTE_MORE.getCode()  + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"批量新增" + tableComment + "信息实例SQL错误\");\n");
		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String persistenceUpdate(String tableComment, String codingHead, String propertyKey) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("更新" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("*@param entity " + tableComment + "信息实例\n");
		insert.append("*@return Boolean true:false=成功:失败\n");
		insert.append("*@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public Boolean update(" + codingHead + "Bean entity) throws SYHCIoTException {\n");
		insert.append("String sql = null;\n");
		insert.append("try {\n");
		insert.append("sql = this." + propertyKey + "_update;\n");
		insert.append("sql = AttributeMappingTool.constractSql(sql, entity);\n");
		insert.append("logger.info(\"-------------更新" + tableComment + "信息实例：\\n\" + sql);\n");
		insert.append("return jdbc.execute(sql) == 1;\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------更新" + tableComment + "信息实例：\" + SYHCIoTExceptionEnum.JDBC_EXECUTE_ONLY.getCode()  + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"更新" + tableComment + "信息实例SQL错误\");\n");
		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String persistenceUpdateDynamicField(String tableComment, String codingHead, String propertyKey, Collection<TableColumnBean> columnList) {
		String primaryParameter = null;
		String primaryValue = null;
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("动态更新" + tableComment + "列字段值\n");
		insert.append("*\n");
		for (TableColumnBean column : columnList) {
			if (column.getPrimaryKey()) {
				insert.append("@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
				if (primaryParameter == null) {
					primaryValue = "{\"" + column.getColumnName() + "\"," + column.getColumnName() + "+\"\"}";
					primaryParameter = column.getJavaSimpleType() + " " + column.getColumnName();
				} else {
					primaryValue = primaryValue + ",{\"" + column.getColumnName() + "\"," + column.getColumnName() + "+\"\"}";
					primaryParameter = primaryParameter + ", " + column.getJavaSimpleType() + " " + column.getColumnName();
				}
			}
		}
		insert.append("*@param parameterList 待更新数据表列字段和字段值\n");
		insert.append("*@return Boolean true:false=成功:失败\n");
		insert.append("*@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public Boolean update(" + primaryParameter + ",List <JdbcParameterBean> parameterList) throws SYHCIoTException {\n");
		insert.append("String sql = null;\n");
		insert.append("String updateField = null;\n");
		insert.append("try {\n");
		insert.append("updateField = AttributeMappingTool.constractUpdateField(parameterList);\n");
		insert.append("sql = this." + propertyKey + "_updateDynamicField;\n");
		insert.append("sql = AttributeMappingTool.constractSql(sql, new String[][]{{\"condition\", updateField}, " + primaryValue + "});\n");
		insert.append("logger.info(\"-------------动态更新" + tableComment + "列字段值：\\n\" + sql);\n");
		insert.append("return jdbc.execute(sql) == 1;\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------动态更新" + tableComment + "列字段值：\" + SYHCIoTExceptionEnum.JDBC_EXECUTE_ONLY.getCode()  + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"动态更新" + tableComment + "列字段值SQL错误\");\n");

		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String persistenceDelete(String tableComment, String codingHead, String propertyKey, TableColumnBean column) {
		String mothedName = "deleteBy" + column.formatJava();

		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "删除" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
		insert.append("*@return Boolean true:false=成功:失败\n");
		insert.append("*@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public Boolean " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ") throws SYHCIoTException {\n");
		insert.append("String sql = null;\n");
		insert.append("try {\n");
		insert.append("sql = this." + propertyKey + "_" + mothedName + ";\n");
		insert.append("sql = AttributeMappingTool.constractSql(sql, \"" + column.getColumnName() + "\"," + column.getColumnName() + "+\"\");\n");
		insert.append("logger.info(\"-------------根据" + column.getColumnComment() + "删除" + tableComment + "信息实例：\\n\" + sql);\n");
		insert.append("return jdbc.execute(sql) == 1;\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------根据" + column.getColumnComment() + "删除" + tableComment + "信息实例：\" + SYHCIoTExceptionEnum.JDBC_EXECUTE_ONLY.getCode()  + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"根据" + column.getColumnComment() + "删除" + tableComment + "信息实例SQL错误\");\n");
		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String persistenceBean(String tableComment, String codingHead, String propertyKey, TableColumnBean column) {
		String mothedName = "queryForBeanBy" + column.formatJava();

		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "获取" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
		insert.append("*@return " + codingHead + "Bean entity " + tableComment + "信息实例\n");
		insert.append("*@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public " + codingHead + "Bean " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ") throws SYHCIoTException {\n");
		insert.append("String sql = null;\n");
		insert.append("try {\n");
		insert.append("sql = this." + propertyKey + "_queryForBean" + "+\" \"+" + "this." + propertyKey + "_" + mothedName + ";\n");
		insert.append("sql = AttributeMappingTool.constractSql(sql, \"" + column.getColumnName() + "\"," + column.getColumnName() + "+\"\");\n");
		insert.append("logger.info(\"-------------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例：\\n\" + sql);\n");
		insert.append("return jdbc.queryForBean(sql, " + codingHead + "Bean.class);\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例：\" + SYHCIoTExceptionEnum.JDBC_SELECT_ONLY.getCode()  + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"根据" + column.getColumnComment() + "获取" + tableComment + "信息实例SQL错误\");\n");
		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String persistenceList(String tableComment, String codingHead, String propertyKey, TableColumnBean column) {
		String mothedName = "queryForListBy" + column.formatJava();

		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合\n");
		insert.append("*\n");
		insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
		insert.append("*@return List<" + codingHead + "Bean> entityList " + tableComment + "信息实例集合\n");
		insert.append("*@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public List<" + codingHead + "Bean> " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ") throws SYHCIoTException {\n");
		insert.append("String sql = null;\n");
		insert.append("try {\n");
		insert.append("sql = this." + propertyKey + "_queryForBean" + "+\" \"+" + "this." + propertyKey + "_" + mothedName + ";\n");
		insert.append("sql = AttributeMappingTool.constractSql(sql, \"" + column.getColumnName() + "\"," + column.getColumnName() + "+\"\");\n");
		insert.append("logger.info(\"-------------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合：\\n\" + sql);\n");
		insert.append("return jdbc.queryForList(sql, " + codingHead + "Bean.class);\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合：\" + SYHCIoTExceptionEnum.JDBC_SELECT_ONLY.getCode() + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合SQL错误\");\n");
		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String persistenceCondition(String tableComment, String codingHead, String propertyKey) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据动态查询条件获取" + tableComment + "信息实例集合\n");
		insert.append("*\n");
		insert.append("*@param paramList 动态查询条件定义\n");
		insert.append("*@param startIndex 查询起始记录行号\n");
		insert.append("*@param pageSize 一次读取记录数\n");
		insert.append("*@return List<" + codingHead + "Bean> entityList 满足条件的" + tableComment + "信息实例集合\n");
		insert.append("*@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public List<" + codingHead + "Bean> queryForListByCondition(List <JdbcParameterBean> paramList,  int startIndex, int pageSize) throws SYHCIoTException {\n");
		insert.append("String sql = null;\n");
		insert.append("try {\n");
		insert.append("String where = null;\n");
		insert.append("if (paramList != null && paramList.isEmpty() == false) {\n");
		insert.append("where = AttributeMappingTool.constractWhere(paramList, null);\n");
		insert.append("}\n");

		insert.append("sql = this." + propertyKey + "_queryForBean" + "+\" \"+" + "this." + propertyKey + "_queryForListByCondition;\n");
		insert.append("sql = AttributeMappingTool.constractSql(sql, new String[][]{{\"startIndex\", startIndex + \"\"}, {\"pageSize\", pageSize + \"\"}, {\"condition\", where}});\n");
		insert.append("logger.info(\"-------------根据根据动态查询条件获取" + tableComment + "信息实例集合：\\n\" + sql);\n");
		insert.append("return jdbc.queryForList(sql, " + codingHead + "Bean.class);\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------根据根据动态查询条件获取" + tableComment + "信息实例集合：\" + SYHCIoTExceptionEnum.JDBC_SELECT_ONLY.getCode()  + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"根据根据动态查询条件获取" + tableComment + "信息实例集合SQL错误\");\n");
		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String persistenceStatistics(String tableComment, String codingHead, String propertyKey) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据动态查询条件统计" + tableComment + "信息实例记录数\n");
		insert.append("*\n");
		insert.append("*@param paramList 动态查询条件定义\n");
		insert.append("*@return Integer totalno 满足条件的" + tableComment + "信息实例记录条数\n");
		insert.append("*@throws SYHCIoTException\n");
		insert.append("*/\n");
		insert.append("public Integer statisticsForListByCondition(List <JdbcParameterBean> paramList) throws SYHCIoTException {\n");
		insert.append("String sql = null;\n");
		insert.append("try {\n");
		insert.append("String where = null;\n");
		insert.append("if (paramList != null && paramList.isEmpty() == false) {\n");
		insert.append("where = AttributeMappingTool.constractWhere(paramList, null);\n");
		insert.append("}\n");

		insert.append("sql = this." + propertyKey + "_statisticsForListByCondition;\n");
		insert.append("sql = AttributeMappingTool.constractSql(sql,\"condition\", where);\n");
		insert.append("logger.info(\"-------------根据动态查询条件统计" + tableComment + "信息实例记录数：\\n\" + sql);\n");
		insert.append(" return jdbc.queryForInteger(sql);\n");
		insert.append("} catch (Exception e) {\n");
		insert.append("logger.error(\"-------------根据根据动态查询条件统计" + tableComment + "信息实例记录数：\" + SYHCIoTExceptionEnum.JDBC_SELECT_ONLY.getCode()  + \"\\n\" + sql, e);\n");
		insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.JDBC_EXECUTE_SQLERROR.getCode(), \"根据根据动态查询条件统计" + tableComment + "信息实例记录数SQL错误\");\n");
		insert.append("}\n");
		insert.append("}\n\n");
		return insert.toString();
	}

}
