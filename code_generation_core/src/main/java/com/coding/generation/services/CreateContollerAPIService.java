package com.coding.generation.services;

import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.coding.generation.domain.TableColumnBean;
import com.coding.jdbc.tools.GeneralUtilTools;

/**
 * WEB逻辑控制器类生成工具
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
public class CreateContollerAPIService {

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

	public String webAPI(String tableName, String tableComment, String packageName, String codingHead, Collection<TableColumnBean> columnList, String defaultMethod[]) {
		String packageArray[] = packageName.split("\\.");
		String lowerHead = codingHead.substring(0, 1).toLowerCase() + codingHead.substring(1);
		TableColumnBean primaryKey = null;
		for (TableColumnBean column : columnList) {
			if (column.getPrimaryKey()) {
				primaryKey = column;
				break;
			}
		}

		StringBuilder persistence = new StringBuilder("package " + packageName + ".web.controller;\n\n");

		persistence.append("import java.util.Calendar;\n");
		persistence.append("import com.alibaba.fastjson.JSON;\n");
		persistence.append("import javax.annotation.Resource;\n");
		persistence.append("import javax.servlet.http.HttpServletRequest;\n");
		persistence.append("import javax.servlet.http.HttpServletResponse;\n");
		persistence.append("import org.springframework.web.bind.annotation.RestController;\n");
		persistence.append("import org.springframework.web.bind.annotation.ModelAttribute;\n");
		persistence.append("import org.springframework.web.bind.annotation.PostMapping;\n");
		persistence.append("import org.springframework.web.bind.annotation.RequestBody;\n");
		persistence.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		persistence.append("import org.springframework.web.bind.annotation.RequestParam;\n");

		persistence.append("import io.swagger.annotations.Api;\n");
		persistence.append("import io.swagger.annotations.ApiImplicitParam;\n");
		persistence.append("import io.swagger.annotations.ApiImplicitParams;\n");
		persistence.append("import io.swagger.annotations.ApiOperation;\n");

		persistence.append("import com.syhc.common.controller.ParentController;\n");
		persistence.append("import com.syhc.common.util.tools.PageLimitTag;\n");
		persistence.append("import com.syhc.jdbc.exception.SYHCIoTException;\n");

		persistence.append("import " + packageName + ".domain." + codingHead + "Bean;\n");
		persistence.append("import " + packageName + ".web.services." + codingHead + "ControllerService;\n");

		persistence.append("/**\n");
		persistence.append(tableComment + "业务逻辑控制器类\n");
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
		persistence.append("@RestController\n");
		persistence.append("@RequestMapping(value = \"/" + packageArray[packageArray.length - 1] + "/" + lowerHead + "\")\n");
		persistence.append("@Api(value = \"" + tableComment + "业务逻辑控制器类\", tags = \"" + tableComment + "业务逻辑控制器类\")\n");
		persistence.append("public class " + codingHead + "Controller extends ParentController {\n\n");

		persistence.append("private static final long serialVersionUID = " + (System.currentTimeMillis() + GeneralUtilTools.getRandomNumericString(5)) + "L;\n");

		persistence.append("@Resource\n");
		persistence.append("private " + codingHead + "ControllerService " + lowerHead + "Logic;\n\n");

		persistence.append(this.dataList(tableComment, codingHead, lowerHead));
		persistence.append(this.dataItem(tableComment, codingHead, lowerHead, primaryKey));
		persistence.append(this.saveForm(tableComment, codingHead, lowerHead));
		persistence.append(this.remove(tableComment, codingHead, lowerHead, primaryKey));

		persistence.append("\n\n");

		persistence.append("}");
		return persistence.toString();
	}

	private String dataList(String tableComment, String codingHead, String lowerHead) {
		StringBuilder dataList = new StringBuilder("/**\n");
		dataList.append("根据动态搜索条件分页加载" + tableComment + "信息实例列表\n");
		dataList.append("*@param search 动态检索条件\n");
		dataList.append("*@param pageTag 数据分页条件\n");
		dataList.append("*@param request 请求报文对象\n");
		dataList.append("*@param response 反馈报文对象\n");
		dataList.append("* @return void\n");
		dataList.append("*/\n");
		dataList.append("@PostMapping(value = \"dataList\")\n");

		dataList.append("\n");

		dataList.append("@ApiOperation(value = \"根据动态搜索条件分页加载" + tableComment + "信息实例列表\", notes = \"根据动态搜索条件分页加载" + tableComment + "信息实例列表\", httpMethod = \"POST\")\n");
		dataList.append("@ApiImplicitParams({\n");
		dataList.append("@ApiImplicitParam(name = \"数据检索条件\", value = \"search\", required = false, paramType = \"form\", dataType = \"String\"),\n");
		dataList.append("@ApiImplicitParam(name = \"列表分页条件\", value = \"pageTag\", required = false, paramType = \"form\", dataType = \"String\")\n");
		dataList.append("})\n");

		dataList.append("public void dataList(@ModelAttribute " + codingHead + "Bean search, @ModelAttribute PageLimitTag pageTag, HttpServletRequest request, HttpServletResponse response) {\n");
		dataList.append("// 受理业务请求\n");
		dataList.append("Calendar requestTime = Calendar.getInstance();\n");
		dataList.append("// 调用业务接口\n");
		dataList.append("SYHCIoTException result = " + lowerHead + "Logic.dataList(search, pageTag);\n");
		dataList.append("// 返回接口数据\n");
		dataList.append("this.outJson(response, result.toJson());\n");
		dataList.append("// 记录日志\n");
		dataList.append("this.operationLog(request, result.getResultCode(), result.getResultMsg(), null, search.toString(), requestTime);\n");
		dataList.append("}\n\n");
		return dataList.toString();
	}

	private String dataItem(String tableComment, String codingHead, String lowerHead, TableColumnBean primaryKey) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("*根据" + tableComment + "序号获取" + tableComment + "基础信息实例\n");
		insert.append("*@param " + primaryKey.getColumnName() + " " + tableComment + "序号\n");
		insert.append("*@param request 请求报文对象\n");
		insert.append("*@param response 反馈报文对象\n");
		insert.append("*@return void\n");
		insert.append("*/\n");
		insert.append("@PostMapping(value = \"/dataItem\")\n");
		insert.append("@ApiOperation(value = \"根据" + tableComment + "序号获取" + tableComment + "基础信息实例\", notes = \"根据" + tableComment + "序号获取" + tableComment + "基础信息实例\", httpMethod = \"POST\")\n");
		insert.append("@ApiImplicitParam(name = \"" + tableComment + "序号\", value = \"" + primaryKey.getColumnName() + "\", required = true, paramType = \"query\", dataType = \"String\")\n");
		insert.append("public void dataItem(String " + primaryKey.getColumnName() + ", HttpServletRequest request, HttpServletResponse response) {\n");
		insert.append("// 受理业务请求\n");
		insert.append("Calendar requestTime = Calendar.getInstance();\n");
		insert.append("// 调用业务接口\n");
		insert.append("SYHCIoTException result = " + lowerHead + "Logic.dataItem(" + primaryKey.getColumnName() + ");\n");
		insert.append("// 返回接口数据\n");
		insert.append("this.outJson(response, result.toJson());\n");
		insert.append("// 记录日志\n");
		insert.append("this.operationLog(request, result.getResultCode(), result.getResultMsg(), " + primaryKey.getColumnName() + ", null, requestTime);\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String saveForm(String tableComment, String codingHead, String lowerHead) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("*保存" + tableComment + "基础信息实例【表单】\n");
		insert.append("*@param entity " + tableComment + "基础信息实例\n");
		insert.append("*@param request 请求报文对象\n");
		insert.append("*@param response 反馈报文对象\n");
		insert.append("*@return void\n");
		insert.append("*/\n");
		insert.append("@PostMapping(value = \"/saveForm\")\n");
		insert.append("@ApiOperation(value = \"保存" + tableComment + "基础信息实例【表单】\", notes = \"保存" + tableComment + "基础信息实例【表单】\", httpMethod = \"POST\")\n");
		insert.append("@ApiImplicitParam(name = \"" + tableComment + "表单数据\", value = \"entity\", required = true, paramType = \"form\", dataType = \"String\")\n");
		insert.append("public void saveForm(@ModelAttribute " + codingHead + "Bean entity, HttpServletRequest request, HttpServletResponse response) {\n");
		insert.append("// 受理业务请求\n");
		insert.append("Calendar requestTime = Calendar.getInstance();\n");
		insert.append("// 调用业务接口\n");
		insert.append("entity.setOperatorId(this.getAccountId(request));\n");
		insert.append("SYHCIoTException result = " + lowerHead + "Logic.saveForm(entity);\n");
		insert.append("// 返回接口数据\n");
		insert.append("this.outJson(response, result.toJson());\n");
		insert.append("// 记录日志\n");
		insert.append("String dataJson = JSON.toJSONString(entity, " + codingHead + "Bean.formFilter());\n");
		insert.append("this.operationLog(request, result.getResultCode(), result.getResultMsg(), null, dataJson, requestTime);\n");
		insert.append("}\n\n");
		return insert.toString();
	}

	private String remove(String tableComment, String codingHead, String lowerHead, TableColumnBean primaryKey) {
		StringBuilder insert = new StringBuilder("/**\n");
		insert.append("*根据" + tableComment + "序号删除" + tableComment + "基础信息实例\n");
		insert.append("*@param " + primaryKey.getColumnName() + " " + tableComment + "序号\n");
		insert.append("*@param request 请求报文对象\n");
		insert.append("*@param response 反馈报文对象\n");
		insert.append("*@return void\n");
		insert.append("*/\n");
		insert.append("@PostMapping(value = \"/remove\")\n");
		insert.append("@ApiOperation(value = \"根据" + tableComment + "序号删除" + tableComment + "基础信息实例\", notes = \"根据" + tableComment + "序号删除" + tableComment + "基础信息实例\", httpMethod = \"POST\")\n");
		insert.append("@ApiImplicitParam(name = \"" + tableComment + "序号\", value = \"" + primaryKey.getColumnName() + "\", required = true, paramType = \"query\", dataType = \"String\")\n");
		insert.append("public void remove(String " + primaryKey.getColumnName() + ", HttpServletRequest request, HttpServletResponse response) {\n");
		insert.append("// 受理业务请求\n");
		insert.append("Calendar requestTime = Calendar.getInstance();\n");
		insert.append("// 调用业务接口\n");
		insert.append("SYHCIoTException result = " + lowerHead + "Logic.remove(" + primaryKey.getColumnName() + ");\n");
		insert.append("// 返回接口数据\n");
		insert.append("this.outJson(response, result.toJson());\n");
		insert.append("// 记录日志\n");
		insert.append("this.operationLog(request, result.getResultCode(), result.getResultMsg(), " + primaryKey.getColumnName() + ", null, requestTime);\n");
		insert.append("}\n\n");
		return insert.toString();
	}

}
