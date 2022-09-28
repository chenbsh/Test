package com.syhc.generation.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coding.jdbc.exception.SYHCException;
import com.syhc.common.controller.ParentController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.coding.generation.domain.MysqlTableReflectionDefineBean;
import com.coding.generation.domain.TableColumnBean;
import com.coding.generation.domain.TableCommentBean;
import com.coding.generation.services.CreateJavaCodingService;
import com.coding.generation.services.CreateJdbcSQLService;
import com.coding.generation.services.MysqlTableReflectionDefinePersistenceLogicService;
import com.coding.generation.services.TableConfigurationService;


/**
 * 代码生成业务逻辑控制器
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
@Controller
@RequestMapping(value = "/code/generation")
public class CodeGenerationController extends ParentController {

	private static final long serialVersionUID = -3375563918491979523L;

	@Resource
	private MysqlTableReflectionDefinePersistenceLogicService defineLogic;

	@Resource
	private TableConfigurationService configurationLogic;

	@Resource
	private CreateJdbcSQLService sqlLogic;

	@Resource
	private CreateJavaCodingService javaLogic;

	/** 获取数据库表信息列表 */
	@RequestMapping(value = "/tableList")
	private ModelAndView tableList(HttpServletRequest request, HttpServletResponse response) {
		List<TableCommentBean> tableList = null;
		try {
			tableList = configurationLogic.queryForTableList();
		} catch (SYHCException e) {
			this.alert(request, e.getMessage());
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		if (tableList == null || tableList.isEmpty()) {
			this.alert(request, "初始化表数据库表信息列表时程序异常");
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		ModelAndView view = new ModelAndView("/views/tableList");
		view.addObject("tableList", tableList);
		return view;
	}

	/** 根据表名称获取表结构基本信息 */
	@RequestMapping(value = "/columnList")
	private ModelAndView columnList(String tableName, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(tableName)) {
			this.alert(request, "表名称不能为空");
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		List<TableColumnBean> columnList = null;
		try {
			columnList = configurationLogic.queryForColumnList(tableName);
		} catch (SYHCException e) {
			this.alert(request, e.getMessage());
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		if (columnList == null || columnList.isEmpty()) {
			this.alert(request, "初始化表列属性时程序异常");
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		String databaseName = columnList.get(0).getDatabaseName();

		ModelAndView view = new ModelAndView("/views/columnList");
		view.addObject("databaseName", databaseName);
		view.addObject("tableName", tableName);
		view.addObject("columnList", columnList);

		MysqlTableReflectionDefineBean define = null;
		try {
			define = defineLogic.queryForBean(databaseName, tableName);
			if (define != null) {
				view.addObject("tableComment", define.getTableComment());
				view.addObject("packageName", define.getPackageName());
				view.addObject("configList", JSON.parseArray(define.getColumnListJson(), TableColumnBean.class));
				view.addObject("methodList", JSON.parseArray(define.getMethodListJson(), String.class));
			}
		} catch (SYHCException e) {
			e.printStackTrace();
		}
		return view;
	}

	/** 生成库表结构对应代码 */
	@RequestMapping(value = "/create")
	private ModelAndView createCoding(@ModelAttribute MysqlTableReflectionDefineBean entity, Boolean existsFlag, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(entity.getDatabaseName())) {
			this.alert(request, "库实例名称不能为空");
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		if (StringUtils.isBlank(entity.getTableName())) {
			this.alert(request, "数据表名称不能为空");
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		if (StringUtils.isBlank(entity.getTableComment())) {
			this.alert(request, "数据表名称注解不能为空");
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		if (StringUtils.isBlank(entity.getPackageName())) {
			this.alert(request, "代码资源包名称不能为空");
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		// 1.库表结构列数据
		Map<String, TableColumnBean> columnMap = null;
		try {
			columnMap = configurationLogic.queryForColumnMap(entity.getTableName());
		} catch (SYHCException e) {
			this.alert(request, e.getMessage());
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}
		if (columnMap == null || columnMap.isEmpty()) {
			this.alert(request, "初始化表列属性时程序异常");
			return new ModelAndView(new RedirectView(request.getContextPath() + "/index.jsp"));
		}

		// 2.接收配置参数
		String parameter = null;
		String parameterValue = null;
		TableColumnBean column = null;

		Enumeration<String> parameterList = request.getParameterNames();
		while (parameterList.hasMoreElements()) {
			parameter = parameterList.nextElement();
			parameterValue = request.getParameter(parameter);
			if (StringUtils.isBlank(parameterValue)) {
				continue;
			}
			if (parameter.endsWith("_alias")) {
				parameter = parameter.replace("_alias", "");
				column = columnMap.get(parameter);
				column.setColumnNameAlias(parameterValue);
			} else if (parameter.endsWith("_remove")) {
				parameter = request.getParameter(parameter + "_method");
				column = columnMap.get(parameterValue);
				column.setRemoveMothod(parameter);
			} else if (parameter.endsWith("_bean")) {
				parameter = request.getParameter(parameter + "_method");
				column = columnMap.get(parameterValue);
				column.setBeanMothod(parameter);
			} else if (parameter.endsWith("_list")) {
				parameter = request.getParameter(parameter + "_method");
				column = columnMap.get(parameterValue);
				column.setListMothod(parameter);
			}
		}
		String defaultMethod[] = request.getParameterValues("defaultMethod");

		String checkBox[] = request.getParameterValues("primarkKey");
		if (checkBox != null) {
			for (String columnName : checkBox) {
				column = columnMap.get(columnName);
				column.setPrimaryKey(true);
			}
		}
		checkBox = request.getParameterValues("searchkey");
		if (checkBox != null) {
			for (String columnName : checkBox) {
				column = columnMap.get(columnName);
				column.setSearchkey(true);
			}
		}

		checkBox = request.getParameterValues("displayList");
		if (checkBox != null) {
			for (String columnName : checkBox) {
				column = columnMap.get(columnName);
				column.setDisplayList(true);
			}
		}

		checkBox = request.getParameterValues("displayForm");
		if (checkBox != null) {
			for (String columnName : checkBox) {
				column = columnMap.get(columnName);
				column.setDisplayForm(true);
			}
		}

		// 3.保存到数据库
		entity.setColumnListJson(JSON.toJSONString(columnMap.values()));
		entity.setMethodListJson(JSON.toJSONString(defaultMethod));
		try {
			if (existsFlag) {
				defineLogic.insert(entity);
			} else {
				defineLogic.update(entity);
			}
		} catch (SYHCException e1) {
			e1.printStackTrace();
		}

		// 4.生成代码
		try {
			sqlLogic.constractSql(entity.getDatabaseName(), entity.getTableName(), entity.getTableComment(), columnMap, defaultMethod);
		} catch (SYHCException e) {
			e.printStackTrace();
		}
		// 5.生产Java代码
		try {
			javaLogic.javaCoding(entity.getDatabaseName(), entity.getTableName(), entity.getTableComment(), entity.getPackageName(), columnMap, defaultMethod);
		} catch (SYHCException e) {
			e.printStackTrace();
		}
		// 6.内存回收
		columnMap.clear();
		defaultMethod = null;
		checkBox = null;

		// 7.跳转页面
		ModelMap paramMap = new ModelMap();
		paramMap.put("tableName", entity.getTableName());
		return new ModelAndView(new RedirectView(request.getContextPath() + "/code/generation/columnList"), paramMap);
	}
}
