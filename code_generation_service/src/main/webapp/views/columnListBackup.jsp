<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>	<title>昇耀合创（厦门）科技有限公司</title>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta name="copyright" content="昇耀合创（厦门）科技有限公司" />
	<meta name="author" content="昇耀合创（厦门）科技有限公司" />
	<meta http-equiv="keywords" content="工业缺陷视觉,工业智慧调度,数智光伏云网,红外消防预警,数智农旅康养,数智赋能车联"/>
	<meta http-equiv="description" content="工业缺陷视觉,工业智慧调度,数智光伏云网,红外消防预警,数智农旅康养,数智赋能车联"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/images/favicon.ico" />
	<link rel="StyleSheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
	<link rel="StyleSheet" type="text/css" href="${pageContext.request.contextPath}/css/manager_button.css" />
	<style type="text/css">
	.formCheckBoxBorder{
		padding-left:14px;
		line-height:28px;
		font-size:14px;
		font-weight: bold;
		border-right: 1px solid #B2D0F6;
	}
	</style>
</head>
<body topmargin="0" leftmargin="0">
	<form id="insertForm" name="insertForm" method="post">
		<table width="99.5%" height="1px" border="0" align="center" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			<tr height="10px"><td></td></tr>
			<tr height="30px">
				<td align="center">
					<table width="98%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0" style="vertical-align: top;background-color: #F0F0F0; border:1px solid #0875D2;">
						<tr height="1px">
							<td width="20%" class="table_title">库实例名称</td>
							<td width="30%" class="table_record_center">${databaseName}&nbsp;</td>
							<td width="20%" class="table_title">数据表注解</td>
							<td width="30%" class="table_record"><input type="text" class="formInputText" id="tableComment" name="tableComment" value="${tableComment}" maxlength="128" placeholder="例如：系统权限菜单" style="font-weight: bold;"/></td>
						</tr>
						<tr height="1px">
							<td class="table_title">数据表名称</td>
							<td class="table_record_center">${tableName}&nbsp;</td>
							<td class="table_title">代码包名称</td>
							<td class="table_record"><input type="text" class="formInputText" id="packageName" name="packageName" value="${packageName}" maxlength="128" placeholder="例如：com.coding.basic"style="font-weight: bold;"/></td>
						</tr>
						<tr height="0px">
							<td><input type="hidden" id="databaseName" name="databaseName" value="${databaseName}"/></td>
							<td><input type="hidden" id="tableName" name="tableName" value="${tableName}"/></td>
							<td>
								<c:choose>
								<c:when test="${configList==null}"><input type="hidden" id="existsFlag" name="existsFlag" value="1"/></c:when>
								<c:otherwise><input type="hidden" id="existsFlag" name="existsFlag" value="0"/></c:otherwise>
								</c:choose>
							</td>
							<td></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr height="2px"><td></td></tr>
			<tr height="1px">
				<td align="center">
					<table width="98%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0" style="vertical-align: top;background-color: #F0F0F0; border:1px solid #0875D2;">
						<tr height="1px">
							<td width="8%" class="table_title">列字段名称</td>
							<td width="6%" class="table_title">主键定义</td>
							<td width="6%" class="table_title">动态查询</td>
							<td width="6%" class="table_title">列表显示</td>
							<td width="6%" class="table_title"><font color="#FFFF00">表单不显</font></td>
							<td width="8%" class="table_title">Java类型</td>
							<td width="5%" class="table_title">数据库类型</td>
							<td width="28%" class="table_title">列字段注解</td>
							<td width="10%" class="table_title">列字段名称</td>
							<td width="17%" class="table_title">列的注解定义</td>
						</tr>
						<c:forEach var="entity" items="${columnList}" step="1" varStatus="forstatus">
						<c:choose>
						<c:when test="${forstatus.index%2==0}">
						<tr height="1px" class="table_tr_out" onmouseout="this.className='table_tr_out'" onmousemove="this.className='table_tr_over'">
						</c:when>
						<c:otherwise>
						<tr height="1px" class="table_tr" onmouseout="this.className='table_tr'" onmousemove="this.className='table_tr_over'">
						</c:otherwise>
						</c:choose>
							<td class="table_record">${entity.columnName}&nbsp;</td>
							<td class="table_record_center"><input type="checkbox" id="primarkKey" name="primarkKey" value="${entity.columnName}"></td>
							<td class="table_record_center"><input type="checkbox" id="searchkey" name="searchkey" value="${entity.columnName}"></td>
							<td class="table_record_center"><input type="checkbox" id="displayList" name="displayList" value="${entity.columnName}"></td>
							<td class="table_record_center"><input type="checkbox" id="displayForm" name="displayForm" value="${entity.columnName}"></td>
							<td class="table_record_center">${entity.javaType}&nbsp;</td>
							<td class="table_record_center">${entity.columnType}&nbsp;</td>
							<td class="table_record">${entity.columnComment}&nbsp;</td>
							<td class="table_record"><a href="javascript:setCodeName('${entity.columnName}');" style="text-decoration:none;">${entity.columnName}</a></td>
							<td class="table_record"><input type="text" class="formInputText" id="${entity.columnName}_alias" name="${entity.columnName}_alias" value="" maxlength="64" placeholder="字母、数字、下划线" style="font-weight: bold;font-size:12px;"/></td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			
			<tr height="2px"><td></td></tr>
			<tr height="1px">
				<td align="center">
					<table width="98%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0" style="vertical-align: top;background-color: #F0F0F0; border:1px solid #0875D2;">
						<tr height="1px">
							<td width="8%" class="table_title">列字段名称</td>
							<td width="24%" class="table_title">数据删除方法</td>
							<td width="8%" class="table_title">列字段名称</td>
							<td width="25%" class="table_title">单条记录查询</td>
							<td width="8%" class="table_title">列字段名称</td>
							<td width="27%" class="table_title">列表记录查询</td>
						</tr>
						<c:forEach var="entity" items="${columnList}" step="1" varStatus="forstatus">
						<c:choose>
						<c:when test="${forstatus.index%2==0}">
						<tr height="1px" class="table_tr_out" onmouseout="this.className='table_tr_out'" onmousemove="this.className='table_tr_over'">
						</c:when>
						<c:otherwise>
						<tr height="1px" class="table_tr" onmouseout="this.className='table_tr'" onmousemove="this.className='table_tr_over'">
						</c:otherwise>
						</c:choose>

							<td class="table_record">${entity.columnName}&nbsp;</td>
							<td class="table_record">
								<table width="100%" height="1px" border="0" align="left" cellspacing="0" cellpadding="0">
									<tr height="1px">
										<td width="10%" align="center"><input type="button" id="${entity.columnName}_remove_method_button" name="${entity.columnName}_remove_method_button" value=" 设  置  " onclick="createMethod('${entity.columnName}','${entity.javaSimpleType}','remove');" /></td></td>
										<td width="90%">
											<input type="hidden" id="${entity.columnName}_remove" name="${entity.columnName}_remove" value=""/>
											<input type="text" class="formInputText" id="${entity.columnName}_remove_method" name="${entity.columnName}_remove_method" value="" placeholder="函数名称自动生成" readonly="readonly" style="font-weight: bold;"/>
										</td>
									</tr>
								</table>
							</td>

							<td class="table_record">${entity.columnName}&nbsp;</td>
							<td class="table_record">
								<table width="100%" height="1px" border="0" align="left" cellspacing="0" cellpadding="0">
									<tr height="1px">
										<td width="10%" align="center"><input type="button" id="${entity.columnName}_bean_method_button" name="${entity.columnName}_bean_method_button" value=" 设  置  " onclick="createMethod('${entity.columnName}','${entity.javaSimpleType}','bean');" /></td></td>
										<td width="90%">
											<input type="hidden" id="${entity.columnName}_bean" name="${entity.columnName}_bean" value=""/>
											<input type="text" class="formInputText" id="${entity.columnName}_bean_method" name="${entity.columnName}_bean_method" value="" placeholder="函数名称自动生成" readonly="readonly" style="font-weight: bold;"/>
										</td>
									</tr>
								</table>
							</td>
							
							<td class="table_record">${entity.columnName}&nbsp;</td>
							<td class="table_record">
								<table width="100%" height="1px" border="0" align="left" cellspacing="0" cellpadding="0">
									<tr height="1px">
										<td width="10%" align="center"><input type="button" id="${entity.columnName}_list_method_button" name="${entity.columnName}_list_method_button" value=" 设  置  " onclick="createMethod('${entity.columnName}','${entity.javaSimpleType}','list');" /></td></td>
										<td width="90%">
											<input type="hidden" id="${entity.columnName}_list" name="${entity.columnName}_list" value=""/>
											<input type="text" class="formInputText" id="${entity.columnName}_list_method" name="${entity.columnName}_list_method" value="" placeholder="函数名称自动生成" readonly="readonly" style="font-weight: bold;"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr height="2px"><td></td></tr>
			<tr height="30px">
				<td align="center">
					<table width="98%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0" style="vertical-align: top;background-color: #F0F0F0; border:1px solid #0875D2;">
					<tr height="30px">
						<td width="10%" rowspan="2" class="table_title">常规方法定义</td>
						<td width="30%" class="formCheckBoxBorder"><input type="checkbox" name="defaultMethod" value="insert" checked="checked">&nbsp;添加一条记录&nbsp;insert</td>
						<td width="30%" class="formCheckBoxBorder"><input type="checkbox" name="defaultMethod" value="update" checked="checked">&nbsp;标准更新记录&nbsp;update</td>
						<td width="30%" class="formCheckBoxBorder"><input type="checkbox" name="defaultMethod" value="condition" checked="checked">&nbsp;动态参数列表查询&nbsp;queryForListByCondition</td>
					</tr>
					<tr height="30px">
						<td width="30%" class="formCheckBoxBorder"><input type="checkbox" name="defaultMethod" value="insertBatch">&nbsp;批量添加记录&nbsp;insertBatch</td>
						<td width="30%" class="formCheckBoxBorder"><input type="checkbox" name="defaultMethod" value="updateDynamicField">&nbsp;动态字段更新&nbsp;updateDynamicField</td>
						<td width="30%" class="formCheckBoxBorder"><input type="checkbox" name="defaultMethod" value="statistics" checked="checked">&nbsp;动态参数统计记录&nbsp;statisticsForListByCondition</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr height="50px">
				<td align="center">
					<input type="button" id="saveId" name="saveId" value=" 生  成  代  码  " onclick="checkInsertForm();" class="button white"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" id="backId" name="backId" value=" 返 回 上 一 页 " onclick="start();" class="button white"/>
				</td>
			</tr>
			<tr><td></td></tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
	var index=0;
	var columnArray= new Array();
	<c:forEach var="entity" items="${columnList}" step="1" varStatus="forstatus">
	columnArray[index]=new Array();
	columnArray[index][0]="${entity.columnName}";
	columnArray[index][1]="${entity.javaType}";
	columnArray[index][2]="${entity.javaSimpleType}";
	index=index+1;
	</c:forEach>

	resetConfigForm();

	function firstCharUpper(source){
		return source.replace(source[0],source[0].toUpperCase());
	}

	function firstCharLower(source){
		return source.replace(source[0],source[0].toLowerCase());
	}



	function setCodeName(columnName){
		var codeName = document.getElementById(columnName+"_alias").value.trim();
		if(codeName==null || codeName==""){
			var length = columnName.length;
			if(columnName.substr(length-2,2).toLowerCase()=="id"){
				document.getElementById(columnName+"_alias").value=columnName.substr(0,length-2)+"Name";
			}else if(columnName.substr(length-4,4).toLowerCase()=="code"){
				document.getElementById(columnName+"_alias").value=columnName.substr(0,length-4)+"Name";
			}else{
				document.getElementById(columnName+"_alias").value=columnName+"Name";
			}
		}else{
			document.getElementById(columnName+"_alias").value="";
		}
	}

	function createMethod(columnName,javaSimpleType,methodType){
		if(methodType=="remove"){
			var removeMethodName = document.getElementById(columnName+"_remove_method").value.trim();
			if(removeMethodName==null || removeMethodName==""){
				document.getElementById(columnName+"_remove").value=columnName;
				document.getElementById(columnName+"_remove_method").value="deleteBy"+firstCharUpper(columnName)+"("+javaSimpleType+" "+columnName+")";
				document.getElementById(columnName+"_remove_method_button").value=" 清  除  ";
			}else{
				document.getElementById(columnName+"_remove").value="";
				document.getElementById(columnName+"_remove_method").value="";
				document.getElementById(columnName+"_remove_method_button").value=" 设  置  ";
			}
			return false;
		}
		var beanMethodName = document.getElementById(columnName+"_bean_method").value.trim();
		var listbeanMethodName = document.getElementById(columnName+"_list_method").value.trim();
		if(methodType=="bean"){
			if(listbeanMethodName!=null && listbeanMethodName!=""){
				document.getElementById(columnName+"_bean").value="";
				document.getElementById(columnName+"_bean_method").value="";
				document.getElementById(columnName+"_bean_method_button").value=" 设  置  ";
				alert("已经存在列表记录查询方法");
				return false;
			}
			if(beanMethodName==null || beanMethodName==""){
				document.getElementById(columnName+"_bean").value=columnName;
				document.getElementById(columnName+"_bean_method").value="queryForBeanBy"+firstCharUpper(columnName)+"("+javaSimpleType+" "+columnName+")";
				document.getElementById(columnName+"_bean_method_button").value=" 清  除  ";
			}else{
				document.getElementById(columnName+"_bean").value="";
				document.getElementById(columnName+"_bean_method").value="";
				document.getElementById(columnName+"_bean_method_button").value=" 设  置  ";
			}
		}else{
			if(beanMethodName!=null && beanMethodName!=""){
				document.getElementById(columnName+"_list").value="";
				document.getElementById(columnName+"_list_method").value="";
				document.getElementById(columnName+"_list_method_button").value=" 设  置  ";
				alert("已经存在单条记录查询方法");
				return false;
			}
			if(listbeanMethodName==null || listbeanMethodName==""){
				document.getElementById(columnName+"_list").value=columnName;
				document.getElementById(columnName+"_list_method").value="queryForListBy"+firstCharUpper(columnName)+"("+javaSimpleType+" "+columnName+")";
				document.getElementById(columnName+"_list_method_button").value=" 清  除  ";
			}else{
				document.getElementById(columnName+"_list").value="";
				document.getElementById(columnName+"_list_method").value="";
				document.getElementById(columnName+"_list_method_button").value=" 设  置  ";
			}
		}
	}


	function checkSearchName(checkname) {
		var filter = /^[A-Za-z0-9_.]+$/;
		return filter.test(checkname);
	}

	function checkInsertForm(){
		var tableName = document.getElementById("tableName").value.trim();
		if(tableName==null || tableName=="" || tableName.length < 4 || checkSearchName(tableName)==false){
			alert("提示：请正确填写数据表名称。");
			document.insertForm.tableName.value="";
			document.insertForm.tableName.focus();
			return false;
		}
		document.getElementById("tableName").value=tableName;
		document.insertForm.action="${pageContext.request.contextPath}/code/generation/columnList";
		document.insertForm.submit();
	}


	function setComboBoxDefault(elementId, default_value_code) {
		var combobox = document.getElementById(elementId);
		var optionLength = combobox.options.length;
		var default_index = 0;
		for (var i = 0; i < optionLength; i++) {
			if (combobox.options[i].value != null
					&& combobox.options[i].value != ""
					&& combobox.options[i].value == default_value_code) {
				default_index = i;
				break;
			}
		}
		document.getElementById(elementId).selectedIndex = default_index;
	}


	function checkInsertForm(){
		var tableComment = document.getElementById("tableComment").value.trim();
		if(tableComment==null || tableComment=="" || tableComment.length < 2){
			alert("提示：请正确填写数据表注解名称。");
			document.insertForm.tableComment.value="";
			document.insertForm.tableComment.focus();
			return false;
		}
		var packageName = document.getElementById("packageName").value.trim();
		if(packageName==null || packageName=="" || packageName.length < 2 || checkSearchName(packageName)==false){
			alert("提示：请正确填写资源包名称。");
			document.insertForm.packageName.value="";
			document.insertForm.packageName.focus();
			return false;
		}

		var total = 0;
		var elements = document.getElementsByName("primarkKey");
		for(var i=0;i<elements.length;i++){
			if(elements[i].checked==true){
				total=total+1;
			}
		}
		if(total==0){
			alert("提示：请设置数据表主键");
			return;
		}

		document.insertForm.action="${pageContext.request.contextPath}/code/generation/create";
		document.insertForm.submit();
	}

	function start(){
		window.location.href="${pageContext.request.contextPath}/code/generation/tableList";
	}

	function resetConfigForm(){
		var elements=null;

		<c:forEach var="entity" items="${configList}" step="1" varStatus="forstatus">
			<c:if test="${entity.columnNameAlias!=null && entity.columnNameAlias!=''}">
				setCodeName("${entity.columnName}");
			</c:if>

			<c:if test="${entity.primaryKey}">
				elements = document.getElementsByName("primarkKey");
				for(var i=0;i<elements.length;i++){
					if(elements[i].value=="${entity.columnName}"){
						elements[i].checked = true;
						break;
					}
				}
			</c:if>

			<c:if test="${entity.searchkey}">
				elements = document.getElementsByName("searchkey");
				for(var i=0;i<elements.length;i++){
					if(elements[i].value=="${entity.columnName}"){
						elements[i].checked = true;
						break;
					}
				}
			</c:if>

			<c:if test="${entity.displayList}">
				elements = document.getElementsByName("displayList");
				for(var i=0;i<elements.length;i++){
					if(elements[i].value=="${entity.columnName}"){
						elements[i].checked = true;
						break;
					}
				}
			</c:if>

			<c:if test="${entity.displayForm}">
				elements = document.getElementsByName("displayForm");
				for(var i=0;i<elements.length;i++){
					if(elements[i].value=="${entity.columnName}"){
						elements[i].checked = true;
						break;
					}
				}
			</c:if>

			<c:if test="${entity.removeMothod!=null && entity.removeMothod!=''}">
				createMethod("${entity.columnName}","${entity.javaSimpleType}","remove");
			</c:if>

			<c:if test="${entity.beanMothod!=null && entity.beanMothod!=''}">
				createMethod("${entity.columnName}","${entity.javaSimpleType}","bean");
			</c:if>

			<c:if test="${entity.listMothod!=null && entity.listMothod!=''}">
				createMethod("${entity.columnName}","${entity.javaSimpleType}","list");
			</c:if>

		</c:forEach>

		<c:forEach var="entity" items="${methodList}" step="1" varStatus="forstatus">
			elements = document.getElementsByName("defaultMethod");
			for(var i=0;i<elements.length;i++){
				if(elements[i].value=="${entity}"){
					elements[i].checked = true;
					break;
				}
			}
		</c:forEach>
	}
	<c:if test="${alertMsg != null}">alert("${alertMsg}");</c:if>
</script>