<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>昇耀合创（厦门）科技有限公司</title>
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
		<table width="98%" height="1px" border="0" align="center" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			<tr height="50px">
				<td colspan="3"></td>
			</tr>
			<tr height="40px">
				<td colspan="3" style="padding-left:15%;padding-top:20px;"><input type="button" id="saveId" name="saveId" value=" 开    始 " onclick="checkInsertForm();" class="button white"/></td>
			</tr>
			<tr height="50px">
				<td colspan="3"></td>
			</tr>
			<tr>
				<td colspan="3"></td>
			</tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">


function checkInsertForm(){
	window.location.href="${pageContext.request.contextPath}/code/generation/tableList";
}
	<c:if test="${alertMsg != null}">alert("${alertMsg}");</c:if>
</script>
<%
	session.invalidate();
%>
