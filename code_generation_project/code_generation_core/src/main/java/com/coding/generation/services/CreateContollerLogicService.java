package com.coding.generation.services;

import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.coding.generation.domain.TableColumnBean;

/**
 * WEB逻辑应用类生成工具
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
public class CreateContollerLogicService {

	// 版权所有人
	@Value("${coding_copyright}")
	private String coding_copyright;

	// 工程项目
	@Value("${coding_project_web}")
	private String coding_project_web;

	// 代码编写工程师
	@Value("${coding_author}")
	private String coding_author;

	// JDK版本
	@Value("${coding_JDK_version}")
	private String coding_JDK_version;

	public String controllerService(String tableName, String tableComment, String packageName, String codingHead, Collection<TableColumnBean> columnList, String defaultMethod[]) {
		String lowerHead = codingHead.substring(0, 1).toLowerCase() + codingHead.substring(1);

		TableColumnBean primaryKey = null;
		for (TableColumnBean column : columnList) {
			if (column.getPrimaryKey()) {
				primaryKey = column;
				break;
			}
		}

		StringBuilder persistence = new StringBuilder("package " + packageName + ".web.services;\n\n");
		persistence.append("import java.util.List;\n");
		persistence.append("import javax.annotation.Resource;\n");
		persistence.append("import org.apache.commons.lang3.StringUtils;\n");
		persistence.append("import org.springframework.stereotype.Repository;\n");
		persistence.append("import com.alibaba.fastjson.JSON;\n");
		persistence.append("import com.syhc.common.util.tools.PageLimitTag;\n");
		persistence.append("import com.syhc.common.constant.SYHCIoTExceptionEnum;\n");
		persistence.append("import com.syhc.jdbc.domain.ResultMsgBean;\n");
		persistence.append("import com.syhc.jdbc.exception.SYHCIoTException;\n");
		persistence.append("import com.syhc.common.utils.GeneralUtilTools;\n");
		persistence.append("import " + packageName + ".domain." + codingHead + "Bean;\n");
		persistence.append("import " + packageName + ".external." + codingHead + "ExternalService;\n");
		persistence.append("\n");
		persistence.append("\n");

		persistence.append("/**\n");
		persistence.append(tableComment + "WEB业务逻辑实现类\n");
		persistence.append("*\n");
		persistence.append("* @Copyright " + this.coding_copyright + "\n");
		persistence.append("* @Project " + this.coding_project_web + "\n");
		persistence.append("* @Author " + this.coding_author + "\n");
		persistence.append("* @timer " + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd") + "\n");
		persistence.append("* @Version 1.0.0\n");
		persistence.append("* @JDK version used " + this.coding_JDK_version + "\n");
		persistence.append("* @Modification history none\n");
		persistence.append("* @Modified by none\n");
		persistence.append("*/\n");
		persistence.append("@Repository\n");
		persistence.append("public class " + codingHead + "ControllerService{\n\n");

		persistence.append("@Resource\n");
		persistence.append("private " + codingHead + "ExternalService " + lowerHead + "Logic;\n\n");

		// 列表函数
		persistence.append(this.dataList(tableComment, codingHead, lowerHead));
		persistence.append(this.dataItem(tableComment, codingHead, lowerHead, primaryKey));
		persistence.append(this.saveForm(tableComment, codingHead, lowerHead, primaryKey));
		persistence.append(this.remove(tableComment, codingHead, lowerHead, primaryKey));

		persistence.append("\n");
		persistence.append("}");
		return persistence.toString();
	}

	private String dataList(String tableComment, String codingHead, String lowerHead) {
		StringBuilder dataList = new StringBuilder("/**\n");
		dataList.append("* 根据动态检索条件获取" + tableComment + "信息实例列表\n");
		dataList.append("* @param search 态检索条件实体Bean\n");
		dataList.append("* @param pageTag 分页标签信息Bean\n");
		dataList.append("* @return SYHCIoTException " + tableComment + "信息实例列表JSON\n");
		dataList.append("*/\n");
		dataList.append("public SYHCIoTException dataList(" + codingHead + "Bean search, PageLimitTag pageTag) {\n");
		dataList.append("ResultMsgBean<List<" + codingHead + "Bean>> msg = " + lowerHead + "Logic.queryForListByCondition(search, pageTag.getStartRow(), pageTag.getPageSize(),true);\n");
		dataList.append("if (msg.getFlag() == ResultMsgBean.NORMAL) {\n");
		dataList.append("// 访问第1页数据\n");
		dataList.append("if (pageTag.getpageIndex() == 1) {\n");
		dataList.append("pageTag.setTotalSize(msg.getLength());\n");
		dataList.append("pageTag.reset();\n");
		dataList.append("}\n");
		dataList.append("String dataJson = JSON.toJSONString(msg.getResult(), " + codingHead + "Bean.listFilter());\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_NORMAL.getCode(), SYHCIoTExceptionEnum.OPERATION_CODE_NORMAL.getName(),pageTag.getpageIndex(), pageTag.getPageSize(), pageTag.getTotalSize(), dataJson);\n");
		dataList.append("} else if (msg.getFlag() == ResultMsgBean.NULLRESULT) {\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_NOTEXIST.getCode(), \"缺少满足条件数据\");\n");
		dataList.append("} else {\n");
		dataList.append("return new SYHCIoTException(msg.getErrorCode(), msg.getMessage());\n");
		dataList.append("}\n");
		dataList.append("}\n\n");
		return dataList.toString();
	}

	private String dataItem(String tableComment, String codingHead, String lowerHead, TableColumnBean primaryKey) {
		StringBuilder dataList = new StringBuilder("/**\n");
		dataList.append("*  根据" + tableComment + "序号获取" + tableComment + "基础信息实例\n");
		dataList.append("* @param " + primaryKey.getColumnName() + " " + tableComment + "序号\n");
		dataList.append("* @return SYHCIoTException " + tableComment + "信息实例JSON\n");
		dataList.append("*/\n");
		dataList.append("public SYHCIoTException dataItem(String " + primaryKey.getColumnName() + ") {\n");
		dataList.append("if (StringUtils.isBlank(" + primaryKey.getColumnName() + ")) {\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_PARAMETER.getCode(), \"" + tableComment + "序号为空\");\n");
		dataList.append("}\n");

		String methodKey = primaryKey.getColumnName().substring(0, 1).toUpperCase() + primaryKey.getColumnName().substring(1);
		dataList.append("ResultMsgBean<" + codingHead + "Bean> dmsg = " + lowerHead + "Logic.queryForBeanBy" + methodKey + "(" + primaryKey.getColumnName() + ");\n");
		dataList.append("if (dmsg.getFlag() == ResultMsgBean.NULLRESULT) {\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_NOTEXIST.getCode(), \"目标" + tableComment + "不存在\");\n");
		dataList.append("} else if (dmsg.getFlag() != ResultMsgBean.NORMAL) {\n");
		dataList.append("return new SYHCIoTException(dmsg.getErrorCode(), dmsg.getMessage());\n");
		dataList.append("} else {\n");
		dataList.append(codingHead + "Bean " + lowerHead + "Entity = dmsg.getResult();\n");
		dataList.append("String dataJson = JSON.toJSONString(" + lowerHead + "Entity, " + codingHead + "Bean.formFilter());\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_NORMAL.getCode(), SYHCIoTExceptionEnum.OPERATION_CODE_NORMAL.getName(), dataJson);\n");
		dataList.append("}\n");
		dataList.append("}\n\n");
		return dataList.toString();
	}

	private String saveForm(String tableComment, String codingHead, String lowerHead, TableColumnBean primaryKey) {
		String methodKey = primaryKey.getColumnName().substring(0, 1).toUpperCase() + primaryKey.getColumnName().substring(1);

		StringBuilder dataList = new StringBuilder("/**\n");
		dataList.append("*  保存" + tableComment + "基础信息实例\n");
		dataList.append("* @param entity " + tableComment + "基础信息实例\n");
		dataList.append("* @return SYHCIoTException 保存" + tableComment + "信息实例结果JSON\n");
		dataList.append("*/\n");
		dataList.append("public SYHCIoTException saveForm(" + codingHead + "Bean entity){\n");
		dataList.append("ResultMsgBean<Boolean> msg = null;\n");
		dataList.append("if (StringUtils.isBlank(entity.get" + methodKey + "())) {\n");
		dataList.append("msg = " + lowerHead + "Logic.insert(entity);\n");
		dataList.append("} else {\n");
		dataList.append("msg = " + lowerHead + "Logic.update(entity);\n");
		dataList.append("}\n");
		dataList.append("if (msg.getFlag() == ResultMsgBean.NORMAL) {\n");
		dataList.append("if (msg.getResult()) {\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_NORMAL.getCode(), \"保存" + tableComment + "-成功\");\n");
		dataList.append("} else {\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_FAILURE.getCode(), \"保存" + tableComment + "-失败\");\n");
		dataList.append("} \n");
		dataList.append("} else {\n");
		dataList.append("return new SYHCIoTException(msg.getErrorCode(), msg.getMessage());\n");
		dataList.append("} \n");
		dataList.append("}\n\n");
		return dataList.toString();
	}

	private String remove(String tableComment, String codingHead, String lowerHead, TableColumnBean primaryKey) {
		String methodKey = primaryKey.getColumnName().substring(0, 1).toUpperCase() + primaryKey.getColumnName().substring(1);
		StringBuilder dataList = new StringBuilder("/**\n");
		dataList.append("*  根据" + tableComment + "序号删除" + tableComment + "基础信息实例\n");
		dataList.append("* @param " + primaryKey.getColumnName() + " " + tableComment + "序号\n");
		dataList.append("* @return SYHCIoTException 删除" + tableComment + "信息实例结果JSON\n");
		dataList.append("*/\n");
		dataList.append("public SYHCIoTException remove(String " + primaryKey.getColumnName() + "){\n");
		dataList.append("if (StringUtils.isBlank(" + primaryKey.getColumnName() + ")) {\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_PARAMETER.getCode(), \"" + tableComment + "序号不能为空\");\n");
		dataList.append("}\n");
		dataList.append("ResultMsgBean<Boolean> msg = " + lowerHead + "Logic.deleteBy" + methodKey + "(" + primaryKey.getColumnName() + ");\n");
		dataList.append("if (msg.getFlag() == ResultMsgBean.NORMAL) {\n");
		dataList.append("if (msg.getResult()) {\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_NORMAL.getCode(), \"删除目标" + tableComment + "信息实例-成功\");\n");
		dataList.append("} else {\n");
		dataList.append("return new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_FAILURE.getCode(),  \"删除目标" + tableComment + "信息实例-失败\");\n");
		dataList.append("}\n");
		dataList.append("} else {\n");
		dataList.append("return new SYHCIoTException(msg.getErrorCode(), msg.getMessage());\n");
		dataList.append("}\n");

		dataList.append("}\n\n");
		return dataList.toString();
	}

}
