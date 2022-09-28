package com.coding.generation.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.coding.generation.domain.TableColumnBean;

/**
 * 数据库表结构对应业务逻辑测试类类生成工具
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
public class CreateJUnitTestLogicService {

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

	public String test(String tableName, String tableComment, String packageName, String codingHead, Collection<TableColumnBean> columnList, String defaultMethod[]) {
		Boolean conditionFlag = false;
		Boolean statisticsFlag = false;

		StringBuilder persistence = new StringBuilder("package " + packageName + ".external.test;\n\n");

		persistence.append("import java.util.List;\n");
		persistence.append("import javax.annotation.Resource;\n");

		persistence.append("import org.junit.Test;\n");
		persistence.append("import org.junit.runner.RunWith;\n");
		persistence.append("import org.springframework.test.context.ContextConfiguration;\n");
		persistence.append("import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;\n");

		persistence.append("import com.alibaba.fastjson.JSON;\n");
		persistence.append("import " + packageName + ".domain." + codingHead + "Bean;\n");
		persistence.append("import " + packageName + ".external." + codingHead + "ExternalService;\n");
		persistence.append("import com.syhc.jdbc.domain.ResultMsgBean;\n");
		persistence.append("import com.syhc.common.utils.GeneralUtilTools;\n");

		persistence.append("/**\n");
		persistence.append(tableComment + "业务逻辑测试类\n");
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

		persistence.append("@RunWith(SpringJUnit4ClassRunner.class)\n");
		persistence.append("@ContextConfiguration(locations = \"classpath:applicationContext-core.xml\")\n");

		persistence.append("public class " + codingHead + "ExternalServiceJunitTest {\n\n");

		persistence.append("public " + codingHead + "ExternalServiceTest() {\n");
		persistence.append("super();\n");
		persistence.append("}\n\n");

		persistence.append("@Resource\n");
		persistence.append("private " + codingHead + "ExternalService external;");

		if (defaultMethod != null) {
			for (String method : defaultMethod) {
				if (method.equals("insert")) {
					persistence.append(this.testInsert(tableComment, codingHead, columnList));
					persistence.append("\n\n");
				} else if (method.equals("insertBatch")) {
					persistence.append(this.testInsertBatch(tableComment, codingHead, columnList));
					persistence.append("\n\n");
				} else if (method.equals("update")) {
					persistence.append(this.testUpdate(tableComment, codingHead, columnList));
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
				persistence.append(this.testDelete(tableComment, codingHead, column));
				persistence.append("\n\n");
			}
		}
		// 生成queryForBean方法
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getBeanMothod())) {
				persistence.append(this.testBean(tableComment, codingHead, column));
				persistence.append("\n\n");
			}
		}
		// 生成queryForList方法
		for (TableColumnBean column : columnList) {
			if (StringUtils.isNotBlank(column.getListMothod())) {
				persistence.append(this.testList(tableComment, codingHead, column));
				persistence.append("\n\n");
			}
		}

		if (conditionFlag) {
			persistence.append(this.testCondition(tableComment, codingHead));
			persistence.append("\n\n");
		}
		if (statisticsFlag) {
			persistence.append(this.testStatistics(tableComment, codingHead));
			persistence.append("\n\n");
		}

		persistence.append("}");
		return persistence.toString();
	}

	private String testInsert(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("新增" + tableComment + "信息实例\n");
		insert.append("*/\n@Test\n");
		insert.append("public void insert() {\n");
		insert.append(codingHead + "Bean entity = new " + codingHead + "Bean();\n");
		for (TableColumnBean column : columnList) {
			if (column.getPrimaryKey() == false) {
				if ((column.getJavaType().equals(Integer.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(0);\n");
				} else if ((column.getJavaType().equals(Long.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(0L);\n");
				} else if ((column.getJavaType().equals(Float.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(0F);\n");
				} else if ((column.getJavaType().equals(Double.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(0D);\n");
				} else if ((column.getJavaType().equals(BigDecimal.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(\"0\");\n");
				} else {
					insert.append("entity.set" + column.formatJava() + "(\"\");\n");
				}
			}
		}

		insert.append("ResultMsgBean <Boolean> msg = external.insert(entity);\n");
		insert.append("if (msg.getFlag() == ResultMsgBean.NORMAL && msg.getResult()) {\n");
		insert.append("System.out.println(\"----------新增" + tableComment + "信息实例：成功----------\");\n");
		insert.append("} else if (msg.getFlag() == ResultMsgBean.NORMAL && msg.getResult() == false) {\n");
		insert.append("System.out.println(\"----------新增" + tableComment + "信息实例：失败----------\");\n");
		insert.append("} else {\n");
		insert.append("System.out.println(\"----------新增" + tableComment + "信息实例：异常----------errorCode=\" + msg.getErrorCode() + \"\terrorMsg=\" + msg.getMessage());\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String testInsertBatch(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("批量新增" + tableComment + "信息实例\n");
		insert.append("*/\n@Test\n");
		insert.append("public void insertBatch() {\n");
		insert.append(codingHead + "Bean entity = null;");
		insert.append("List<" + codingHead + "Bean> entityList = new ArrayList<" + codingHead + "Bean>();\n\n");
		for (int i = 0; i < 3; i++) {
			insert.append("entity = new " + codingHead + "Bean();\n");
			for (TableColumnBean column : columnList) {
				if (column.getPrimaryKey()) {
					insert.append("entity.set" + column.formatJava() + "(GeneralUtilTools.getPrimaryKey());\n");
				} else if ((column.getJavaType().equals(Integer.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(0);\n");
				} else if ((column.getJavaType().equals(Long.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(0L);\n");
				} else if ((column.getJavaType().equals(Float.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(0F);\n");
				} else if ((column.getJavaType().equals(Double.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(0D);\n");
				} else if ((column.getJavaType().equals(BigDecimal.class.getName()))) {
					insert.append("entity.set" + column.formatJava() + "(\"0\");\n");
				} else {
					insert.append("entity.set" + column.formatJava() + "(\"\");\n");
				}
			}
			insert.append("entityList.add(entity);\n\n");
		}
		insert.append("ResultMsgBean <Boolean> msg = external.insert(entityList);\n");
		insert.append("if (msg.getFlag() == ResultMsgBean.NORMAL && msg.getResult()) {\n");
		insert.append("System.out.println(\"----------批量新增" + tableComment + "信息实例：成功----------\");\n");
		insert.append("} else if (msg.getFlag() == ResultMsgBean.NORMAL && msg.getResult() == false) {\n");
		insert.append("System.out.println(\"----------批量新增" + tableComment + "信息实例：失败----------\");\n");
		insert.append("} else {\n");
		insert.append("System.out.println(\"----------批量新增" + tableComment + "信息实例：异常----------errorCode=\" + msg.getErrorCode() + \"\terrorMsg=\" + msg.getMessage());\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String testUpdate(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("更新" + tableComment + "信息实例\n");
		insert.append("*/\n@Test\n");
		insert.append("public void update() {\n");
		insert.append(codingHead + "Bean entity = new " + codingHead + "Bean();\n");
		for (TableColumnBean column : columnList) {
			if (column.getPrimaryKey()) {
				insert.append("entity.set" + column.formatJava() + "(GeneralUtilTools.getPrimaryKey());\n");
			} else if ((column.getJavaType().equals(Integer.class.getName()))) {
				insert.append("entity.set" + column.formatJava() + "(0);\n");
			} else if ((column.getJavaType().equals(Long.class.getName()))) {
				insert.append("entity.set" + column.formatJava() + "(0L);\n");
			} else if ((column.getJavaType().equals(Float.class.getName()))) {
				insert.append("entity.set" + column.formatJava() + "(0F);\n");
			} else if ((column.getJavaType().equals(Double.class.getName()))) {
				insert.append("entity.set" + column.formatJava() + "(0D);\n");
			} else if ((column.getJavaType().equals(BigDecimal.class.getName()))) {
				insert.append("entity.set" + column.formatJava() + "(\"0\");\n");
			} else {
				insert.append("entity.set" + column.formatJava() + "(\"\");\n");
			}
		}

		insert.append("ResultMsgBean <Boolean> msg = external.update(entity);\n");
		insert.append("if (msg.getFlag() == ResultMsgBean.NORMAL && msg.getResult()) {\n");
		insert.append("System.out.println(\"----------更新" + tableComment + "信息实例：成功----------\");\n");
		insert.append("} else if (msg.getFlag() == ResultMsgBean.NORMAL && msg.getResult() == false) {\n");
		insert.append("System.out.println(\"----------更新" + tableComment + "信息实例：失败----------\");\n");
		insert.append("} else {\n");
		insert.append("System.out.println(\"----------更新" + tableComment + "信息实例：异常----------errorCode=\" + msg.getErrorCode() + \"\terrorMsg=\" + msg.getMessage());\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String testDelete(String tableComment, String codingHead, TableColumnBean column) {
		String mothedName = "deleteBy" + column.formatJava();

		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "删除" + tableComment + "信息实例\n");
		insert.append("*/\n@Test\n");
		insert.append("public void " + mothedName + "() {\n");

		if ((column.getJavaType().equals(Integer.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0;\n");
		} else if ((column.getJavaType().equals(Long.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0L;\n");
		} else if ((column.getJavaType().equals(Float.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0F;\n");
		} else if ((column.getJavaType().equals(Double.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0D;\n");
		} else if ((column.getJavaType().equals(BigDecimal.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=\"0\";\n");
		} else {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=\"\";\n");
		}

		insert.append("ResultMsgBean <Boolean> msg = external." + mothedName + "(" + column.getColumnName() + ");\n");
		insert.append("if (msg.getFlag() == ResultMsgBean.NORMAL && msg.getResult()) {\n");
		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "删除" + tableComment + "信息实例：成功----------\");\n");
		insert.append("} else if (msg.getFlag() == ResultMsgBean.NORMAL && msg.getResult() == false) {\n");
		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "删除" + tableComment + "信息实例：失败----------\");\n");
		insert.append("} else {\n");
		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "删除" + tableComment + "信息实例：异常----------errorCode=\" + msg.getErrorCode() + \"\terrorMsg=\" + msg.getMessage());\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String testBean(String tableComment, String codingHead, TableColumnBean column) {
		String mothedName = "queryForBeanBy" + column.formatJava();
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "获取" + tableComment + "信息实例\n");
		insert.append("*/\n@Test\n");
		insert.append("public void " + mothedName + "() {\n");

		if ((column.getJavaType().equals(Integer.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0;\n");
		} else if ((column.getJavaType().equals(Long.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0L;\n");
		} else if ((column.getJavaType().equals(Float.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0F;\n");
		} else if ((column.getJavaType().equals(Double.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0D;\n");
		} else if ((column.getJavaType().equals(BigDecimal.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=\"0\";\n");
		} else {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=\"\";\n");
		}

		insert.append("ResultMsgBean <" + codingHead + "Bean> msg = external." + mothedName + "(" + column.getColumnName() + ");\n");
		insert.append("if (msg.getFlag() == ResultMsgBean.NORMAL) {\n");
		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例：成功----------\");\n");
		insert.append("System.out.println(JSON.toJSONString(msg.getResult()));\n");
		insert.append("} else if (msg.getFlag() == ResultMsgBean.NULLRESULT) {\n");
		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例：未能找到符合条件数据----------\");\n");
		insert.append("} else {\n");
		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例：异常----------errorCode=\" + msg.getErrorCode() + \"\terrorMsg=\" + msg.getMessage());\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String testList(String tableComment, String codingHead, TableColumnBean column) {
		String mothedName = "queryForListBy" + column.formatJava();

		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合\n");
		insert.append("*/\n@Test\n");
		insert.append("public void " + mothedName + "() {\n");

		if ((column.getJavaType().equals(Integer.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0;\n");
		} else if ((column.getJavaType().equals(Long.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0L;\n");
		} else if ((column.getJavaType().equals(Float.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0F;\n");
		} else if ((column.getJavaType().equals(Double.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=0D;\n");
		} else if ((column.getJavaType().equals(BigDecimal.class.getName()))) {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=\"0\";\n");
		} else {
			insert.append(column.getJavaSimpleType() + " " + column.getColumnName() + "=\"\";\n");
		}

		insert.append("ResultMsgBean<List<" + codingHead + "Bean>> msg = external." + mothedName + "(" + column.getColumnName() + ");\n");
		insert.append("if (msg.getFlag() == ResultMsgBean.NORMAL) {\n");

		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合：成功----------\");\n");
		insert.append("for (" + codingHead + "Bean entity : msg.getResult()) {\n");
		insert.append("System.out.println(JSON.toJSONString(entity));\n");
		insert.append("}\n");

		insert.append("System.out.println(JSON.toJSONString(msg.getResult()));\n");
		insert.append("} else if (msg.getFlag() == ResultMsgBean.NULLRESULT) {\n");
		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合：未能找到符合条件数据----------\");\n");
		insert.append("} else {\n");
		insert.append("System.out.println(\"----------根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合：异常----------errorCode=\" + msg.getErrorCode() + \"\terrorMsg=\" + msg.getMessage());\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();
	}

	private String testCondition(String tableComment, String codingHead) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据动态查询条件获取" + tableComment + "信息实例集合\n");
		insert.append("*/\n@Test\n");
		insert.append("public void queryForListByCondition() {\n");

		insert.append(codingHead + "Bean search=null;\n");
		insert.append("int startIndex=0;\n");
		insert.append("int pageSize=15;\n");

		insert.append("ResultMsgBean<List<" + codingHead + "Bean>> msg=external.queryForListByCondition(search,startIndex,pageSize,true);\n");
		insert.append("if (msg.getFlag() == ResultMsgBean.NORMAL) {\n");
		insert.append("System.out.println(\"----------根据根据动态查询条件获取" + tableComment + "信息实例集合：成功----------\");\n");
		insert.append("for (" + codingHead + "Bean entity : msg.getResult()) {\n");
		insert.append("System.out.println(JSON.toJSONString(entity));\n");
		insert.append("}\n");
		insert.append("} else if (msg.getFlag() == ResultMsgBean.NULLRESULT) {\n");
		insert.append("System.out.println(\"----------根据根据动态查询条件获取" + tableComment + "信息实例集合：未能找到符合条件数据----------\");\n");
		insert.append("} else {\n");
		insert.append("System.out.println(\"----------根据根据动态查询条件获取" + tableComment + "信息实例集合：异常----------errorCode=\" + msg.getErrorCode() + \"\terrorMsg=\" + msg.getMessage());\n");
		insert.append("}\n");

		insert.append("}\n\n");
		return insert.toString();

	}

	private String testStatistics(String tableComment, String codingHead) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("根据动态条件统计满足条件" + tableComment + "信息实例记录数\n");
		insert.append("*/\n@Test\n");
		insert.append("public void  statisticsForListByCondition()  {\n");

		insert.append(codingHead + "Bean search=null;\n");
		insert.append("ResultMsgBean<Integer> msg=external.statisticsForListByCondition(search);\n");
		insert.append("if (msg.getFlag() == ResultMsgBean.NORMAL) {\n");
		insert.append("System.out.println(\"----------根据动态条件统计满足条件" + tableComment + "信息实例记录数【\"+msg.getResult()+\"】：成功----------\");\n");
		insert.append("} else {\n");
		insert.append("System.out.println(\"----------根据根据动态查询条件获取" + tableComment + "信息实例集合：异常----------errorCode=\" + msg.getErrorCode() + \"\terrorMsg=\" + msg.getMessage());\n");
		insert.append("}\n\n");
		insert.append("}\n");
		return insert.toString();
	}

}
