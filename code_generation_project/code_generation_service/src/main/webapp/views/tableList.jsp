<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
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
</head>
<body topmargin="0" leftmargin="0">
	<form id="insertForm" name="insertForm" method="post">
		<table width="99.5%" height="1px" border="0" align="center" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			<tr height="10px"><td></td></tr>
			<tr height="30px">
				<td align="center">
					<table width="98%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0" style="vertical-align: top;background-color: #F0F0F0; border:1px solid #0875D2;">
						<tr height="1px">
							<td width="20%" class="table_title">数据库实例</td>
							<td width="20%" class="table_title">数据库表名称</td>
							<td width="20%" class="table_title">数据库表实例</td>
							<td width="20%" class="table_title">表排序字符集</td>
							<td width="20%" class="table_title">库表创建时间</td>
						</tr>
						<c:forEach var="entity" items="${tableList}" step="1" varStatus="forstatus">
						<c:choose>
						<c:when test="${forstatus.index%2==0}">
						<tr height="1px" class="table_tr_out" onmouseout="this.className='table_tr_out'" onmousemove="this.className='table_tr_over'">
						</c:when>
						<c:otherwise>
						<tr height="1px" class="table_tr" onmouseout="this.className='table_tr'" onmousemove="this.className='table_tr_over'">
						</c:otherwise>
						</c:choose>
						
							<td class="table_record">${entity.databaseName}&nbsp;</td>
							<td class="table_record">${entity.tableComment}&nbsp;</td>
							<td class="table_record"><a href="${pageContext.request.contextPath}/code/generation/columnList?tableName=${entity.tableName}" style="text-decoration: none;">${entity.tableName}</a></td>
							<td class="table_record">${entity.tableCollation}&nbsp;</td>
							<td class="table_record">${entity.createTime}&nbsp;</td>
							
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr height="50px">
				<td align="center">
					<input type="button" id="backId" name="backId" value=" 返 回 上 一 页 " onclick="start();" class="button white"/>
				</td>
			</tr>
			<tr><td></td></tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
	<c:if test="${alertMsg != null}">alert("${alertMsg}");</c:if>
</script>