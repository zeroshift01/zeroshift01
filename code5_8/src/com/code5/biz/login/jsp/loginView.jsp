<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	response.setHeader("cache-control", "no-cache");
	response.setHeader("expires", "0");
	response.setHeader("pragma", "no-cache");

	Box box = BoxContext.getThread();

	String msg = box.getAlertMsg();
	
	
%>
<html><head>
<script type="text/javascript">

function login(){
	var form = document.form1;
	form.action = '/waf/login';
	form.submit();
} 

</script>
<body>

<%=msg %>

<form name="form1" method="post">

<br>ID
<br><input type="text" name="ID">

<br>PIN
<br><input type="text" name="PIN">

</form>

<button onclick="login()">로그인</button>

</body>
</head></html>

