package com.coding.generation.services;

import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.coding.generation.domain.TableColumnBean;

/**
 * 业务逻辑外部调用类生成工具
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
public class CreateExternalLogicService {

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

	public String external(String tableName, String tableComment, String packageName, String codingHead, Collection<TableColumnBean> columnList, String defaultMethod[]) {
		Boolean conditionFlag = false;
		Boolean statisticsFlag = false;

		StringBuilder persistence = new StringBuilder("package " + packageName + ".external;\n\n");

		persistence.append("import " + packageName + ".domain." + codingHead + "Bean;\n");
		persistence.append("import " + packageName + ".internal." + codingHead + "InternalService;\n");
		persistence.append("import com.syhc.jdbc.domain.ResultMsgBean;\n");
		persistence.append("import com.syhc.jdbc.domain.JdbcParameterBean;\n");
		persistence.append("import com.syhc.jdbc.exception.SYHCIoTException;\n");
		persistence.append("import org.springframework.stereotype.Repository;\n");
		persistence.append("import javax.annotation.Resource;\n");
		persistence.append("import java.util.List;\n");
		persistence.append("import org.slf4j.Logger;\n");
		persistence.append("import org.slf4j.LoggerFactory;\n\n");

		persistence.append("/**\n");
		persistence.append(tableComment + "业务逻辑外部调用类\n");
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

		persistence.append("@Repository\n");
		persistence.append("public class " + codingHead + "ExternalService {\n\n");

		persistence.append("public " + codingHead + "ExternalService() {\n");
		persistence.append("super();\n");
		persistence.append("}\n\n");

		persistence.append("private final Logger logger = LoggerFactory.getLogger(this.getClass());\n\n");

		persistence.append("@Resource\n");
		persistence.append("private " + codingHead + "InternalService internal;\n\n");

		if (defaultMethod != null) {
			for (String method : defaultMethod) {
				if (method.equals("insert")) {
					persistence.append(this.externalInsert(tableComment, codingHead, columnList));
					persistence.append("\n\n");
				} else if (method.equals("insertBatch")) {
					persistence.append(this.externalInsertBatch(tableComment, codingHead, columnList));
					persistence.append("\n\n");
				} else if (method.equals("update")) {
					persistence.append(this.externalUpdate(tableComment, codingHead, columnList));
					persistence.append("\n\n");
				} else if (method.equals("condition")) {
					conditionFlag = true;
				} else if (method.equals("statistics")) {
					statisticsFlag = true;
				}
			}
		}

		// 生成delete方法
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getRemoveMothod())) {
				persistence.append(this.externalDelete(tableComment, codingHead, column));
				persistence.append("\n\n");
			}
		}
		// 生成queryForBean方法
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getBeanMothod())) {
				persistence.append(this.externalBean(tableComment, codingHead, column));
				persistence.append("\n\n");
			}
		}
		// 生成queryForList方法
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getListMothod())) {
				persistence.append(this.externalList(tableComment, codingHead, column));
				persistence.append("\n\n");
			}
		}

		if (conditionFlag) {
			persistence.append(this.externalCondition(tableComment, codingHead));
			persistence.append("\n\n");
		}
		if (statisticsFlag) {
			persistence.append(this.externalStatistics(tableComment, codingHead));
			persistence.append("\n\n");
		}

		persistence.append("}");
		return persistence.toString();
	}

	private String externalInsert(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("新增" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("@param entity " + tableComment + "信息实例\n");
		insert.append("@return ResultMsgBean<Boolean>［flag=1,result=true（成功）］［flag=1,result=false （失败）］［flag=-1:可见异常］［flag=-2:运行时异常］\n");
		insert.append("*/\n");
		insert.append("public ResultMsgBean <Boolean> insert(" + codingHead + "Bean entity) {\n");

		insert.append("try {\n");
		insert.append("Boolean result = internal.insert(entity);\n");
		insert.append("return new ResultMsgBean <Boolean>(result);\n");
		insert.append("} catch (SYHCIoTException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("return new ResultMsgBean <Boolean>(e.getResultCode(), e.getResultMsg());\n");
		insert.append("} catch (RuntimeException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("logger.warn(\"新增" + tableComment + "信息实例\", e);\n");
		insert.append("return new ResultMsgBean <Boolean>();\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String externalInsertBatch(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("批量新增" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("*@param entityList " + tableComment + "信息实例集合\n");
		insert.append("@return ResultMsgBean<Boolean>［flag=1,result=true（成功）］［flag=1,result=false （失败）］［flag=-1:可见异常］［flag=-2:运行时异常］\n");
		insert.append("*/\n");
		insert.append("public ResultMsgBean<Boolean> insert(List <" + codingHead + "Bean> entityList)  {\n");

		insert.append("try {\n");
		insert.append("Boolean result = internal.insert(entityList);\n");
		insert.append("return new ResultMsgBean <Boolean>(result);\n");
		insert.append("} catch (SYHCIoTException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("return new ResultMsgBean <Boolean>(e.getResultCode(), e.getResultMsg());\n");
		insert.append("} catch (RuntimeException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("logger.warn(\"批量新增" + tableComment + "信息实例\", e);\n");
		insert.append("return new ResultMsgBean <Boolean>();\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String externalUpdate(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("更新" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("@param entity " + tableComment + "信息实例\n");
		insert.append("@return ResultMsgBean<Boolean>［flag=1,result=true（成功）］［flag=1,result=false （失败）］［flag=-1:可见异常］［flag=-2:运行时异常］\n");
		insert.append("*/\n");
		insert.append("public ResultMsgBean<Boolean> update(" + codingHead + "Bean entity){\n");

		insert.append("try {\n");
		insert.append("Boolean result = internal.update(entity);\n");
		insert.append("return new ResultMsgBean <Boolean>(result);\n");
		insert.append("} catch (SYHCIoTException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("return new ResultMsgBean <Boolean>(e.getResultCode(), e.getResultMsg());\n");
		insert.append("} catch (RuntimeException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("logger.warn(\"更新" + tableComment + "信息实例\", e);\n");
		insert.append("return new ResultMsgBean <Boolean>();\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String externalDelete(String tableComment, String codingHead, TableColumnBean column) {
		String mothedName = "deleteBy" + column.formatJava();

		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "删除" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
		insert.append("@return ResultMsgBean<Boolean>［flag=1,result=true（成功）］［flag=1,result=false （失败）］［flag=-1:可见异常］［flag=-2:运行时异常］\n");
		insert.append("*/\n");
		insert.append("public ResultMsgBean<Boolean> " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ") {\n");

		insert.append("try {\n");
		insert.append("Boolean result = internal." + mothedName + "(" + column.getColumnName() + ");\n");
		insert.append("return new ResultMsgBean <Boolean>(result);\n");
		insert.append("} catch (SYHCIoTException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("return new ResultMsgBean <Boolean>(e.getResultCode(), e.getResultMsg());\n");
		insert.append("} catch (RuntimeException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("logger.warn(\"根据" + column.getColumnComment() + "删除" + tableComment + "信息实例\", e);\n");
		insert.append("return new ResultMsgBean <Boolean>();\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String externalBean(String tableComment, String codingHead, TableColumnBean column) {
		String mothedName = "queryForBeanBy" + column.formatJava();

		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "获取" + tableComment + "信息实例\n");
		insert.append("*\n");
		insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
		insert.append("*@return " + codingHead + "Bean ［flag=1,result=信息实例］［flag=2,result=数据不存在］［flag=-1:可见异常］［flag=-2:运行时异常］\n");
		insert.append("*/\n");
		insert.append("public ResultMsgBean <" + codingHead + "Bean> " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ") {\n");

		insert.append("try {\n");
		insert.append(codingHead + "Bean entity=internal." + mothedName + "(" + column.getColumnName() + ");\n");
		insert.append("if (entity == null) {\n");
		insert.append("return new ResultMsgBean <" + codingHead + "Bean>(ResultMsgBean.NULLRESULT);\n");
		insert.append("} else {\n");
		insert.append("return new ResultMsgBean <" + codingHead + "Bean>(entity);\n");
		insert.append("}\n");
		insert.append("} catch (SYHCIoTException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("return new ResultMsgBean <" + codingHead + "Bean>(e.getResultCode(), e.getResultMsg());\n");
		insert.append("} catch (RuntimeException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("logger.warn(\"根据" + column.getColumnComment() + "获取" + tableComment + "信息实例\", e);\n");
		insert.append("return new ResultMsgBean <" + codingHead + "Bean>();\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String externalList(String tableComment, String codingHead, TableColumnBean column) {
		String mothedName = "queryForListBy" + column.formatJava();

		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合\n");
		insert.append("*\n");
		insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
		insert.append("*@return ResultMsgBean<List<" + codingHead + "Bean>> ［flag=1,result=信息实例列表,length＝记录数量］［flag=2,result=数据不存在］［flag=-1:可见异常］［flag=-2:运行时异常］\n");
		insert.append("*/\n");
		insert.append("public ResultMsgBean<List<" + codingHead + "Bean>> " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ")  {\n");

		insert.append("try {\n");
		insert.append("List<" + codingHead + "Bean> entityList=internal." + mothedName + "(" + column.getColumnName() + ");\n");
		insert.append("if (entityList == null || entityList.isEmpty()) {\n");
		insert.append("return new ResultMsgBean<List<" + codingHead + "Bean>>(ResultMsgBean.NULLRESULT);\n");
		insert.append("} else {\n");
		insert.append("return new ResultMsgBean<List<" + codingHead + "Bean>>(entityList,entityList.size());\n");
		insert.append("}\n");
		insert.append("} catch (SYHCIoTException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("return new ResultMsgBean<List<" + codingHead + "Bean>>(e.getResultCode(), e.getResultMsg());\n");
		insert.append("} catch (RuntimeException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("logger.warn(\"根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合\", e);\n");
		insert.append("return new ResultMsgBean<List<" + codingHead + "Bean>>();\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String externalCondition(String tableComment, String codingHead) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据动态查询条件获取" + tableComment + "信息实例集合\n");
		insert.append("*\n");
		insert.append("*@param search 动态查询条件定义\n");
		insert.append("*@param startIndex 查询起始记录行号\n");
		insert.append("*@param pageSize 一次读取记录数\n");
		insert.append("*@param countFlag  是否统计关联记录【true=需要:false=不需要】\n");
		insert.append("*@return ResultMsgBean<List<" + codingHead + "Bean>> ［flag=1,result=信息实例列表,length＝记录数量］［flag=2,result=数据不存在］［flag=-1:可见异常］［flag=-2:运行时异常］\n");
		insert.append("*/\n");
		insert.append("public ResultMsgBean<List<" + codingHead + "Bean>> queryForListByCondition(" + codingHead + "Bean search, int startIndex, int pageSize, Boolean countFlag) {\n");

		insert.append("try {\n");

		insert.append("List <JdbcParameterBean> paramList = internal.constract(search);\n");
		insert.append("Integer length = 0;\n");
		insert.append("if (startIndex == 0) {\n");
		insert.append("length = internal.statisticsForListByCondition(paramList).intValue();\n");
		insert.append("if (length == 0) {\n");
		insert.append("if (paramList != null) {\n");
		insert.append("paramList.clear();\n");
		insert.append("}\n");
		insert.append("return new ResultMsgBean <List <" + codingHead + "Bean>>(ResultMsgBean.NULLRESULT);\n");
		insert.append("}\n");
		insert.append("}\n");

		insert.append("List<" + codingHead + "Bean> entityList=internal.queryForListByCondition(paramList,startIndex,pageSize,countFlag);\n");

		insert.append("if (paramList != null) {\n");
		insert.append("paramList.clear();\n");
		insert.append("}\n");

		insert.append("if (entityList == null || entityList.isEmpty()) {\n");
		insert.append("return new ResultMsgBean<List<" + codingHead + "Bean>>(ResultMsgBean.NULLRESULT);\n");
		insert.append("} else {\n");
		insert.append("return new ResultMsgBean<List<" + codingHead + "Bean>>(entityList,length);\n");
		insert.append("}\n");
		insert.append("} catch (SYHCIoTException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("return new ResultMsgBean<List<" + codingHead + "Bean>>(e.getResultCode(), e.getResultMsg());\n");
		insert.append("} catch (RuntimeException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("logger.warn(\"根据动态查询条件获取" + tableComment + "信息实例集合\", e);\n");
		insert.append("return new ResultMsgBean<List<" + codingHead + "Bean>>();\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String externalStatistics(String tableComment, String codingHead) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据动态条件统计满足条件" + tableComment + "信息实例记录数\n");
		insert.append("*\n");
		insert.append("*@param search 动态查询条件定义\n");
		insert.append("*@return ResultMsgBean<Integer> ［flag=1,result=记录数量］［flag=2,result=数据不存在］［flag=-1:可见异常］［flag=-2:运行时异常］\n");
		insert.append("*/\n");
		insert.append("public ResultMsgBean<Integer>  statisticsForListByCondition(" + codingHead + "Bean search)  {\n");
		insert.append("try {\n");
		insert.append("List <JdbcParameterBean> paramList = internal.constract(search);\n");
		insert.append("Integer length = internal.statisticsForListByCondition(paramList).intValue();\n");
		insert.append("return new ResultMsgBean <Integer>(length);\n");
		insert.append("} catch (SYHCIoTException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("return new ResultMsgBean <Integer>(e.getResultCode(), e.getResultMsg());\n");
		insert.append("} catch (RuntimeException e) {\n");
		insert.append("e.printStackTrace();\n");
		insert.append("logger.warn(\"根据动态条件统计满足条件" + tableComment + "信息实例记录数\", e);\n");
		insert.append("return new ResultMsgBean <Integer>();\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

}
