<%@page import="com.code5.fw.trace.Trace"%>
<%@page import="com.code5.fw.data.Box"%>
<%@page import="com.code5.fw.web.BoxContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%



	Box box = BoxContext.getThread();

	String ret = box.s("ret");
	
	Trace trace = new Trace(this);
	for(int i=0; i < 1000000; i ++) {
		trace.write(box.hashCode()+" "+i);
	}
	

%>
<html>
<head>
<script type="text/javascript">
	function login() {
		var form = document.form1;
		form.action = '/waf/login';
		form.submit();
	}
</script>
<body>

	<%=ret %>

	<form name="form1" method="post">

		<br>ID <br>
		<input type="text" name="ID"> <br>PIN <br>
		<input type="text" name="PIN">

	</form>

	<button onclick="login()">로그인</button>

</body>
</head>
</html>