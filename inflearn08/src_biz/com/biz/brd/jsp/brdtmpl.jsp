<%@page import="com.code5.fw.data.SessionB"%>
<%@page import="com.code5.fw.data.Table"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

Box box = BoxContext.get();
Box fwView = box.getBox(Box.KEY_FW_VIEW);
String view = fwView.s("VIEW");
String title = fwView.s("TITLE");

SessionB user = box.getSessionB();

%>
<html>
<head>
<title><%=title%></title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="/css/main.css" />
<script>
	function init() {
		var msg = document.mainform.ALERT_MSG.value;
		if (msg != '') {
			alert(msg);
		}
	}
	
	function brd01020(){
		var form = document.mainform;
		form.action = '/waf/brd01020';
		form.submit();
	}
	
	function brd01010(){
		var form = document.mainform;
		form.action = '/waf/brd01010';
		form.submit();
	}
	
	function exeLogout(){
		var form = document.mainform;
		form.action = '/waf/exeLogout';
		form.submit();
	}
	
</script>
<body onload='init();'>
<br>로그인한 아이디 : <%=user.getId() %>, 권한코드 : <%=user.getAuth() %>
<br><button onclick="brd01020()">brd01020</button> <button onclick="brd01010()">brd01010</button> <button onclick="exeLogout()">logout</button>
<hr>
메뉴명:<%=title%>
<jsp:include page="<%=view%>" flush="true"/>
<hr>
<form name='mainform' method="post">
mainform.ALERT_MSG : <input id='TEXT' name='ALERT_MSG' value='<%=box.getAlertMsg()%>' />
</form>
</body>
</head>
</html>