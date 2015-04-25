<%@page import="at.reisisoft.convert.ConverterInformation"%>
<%@page import="at.reisisoft.convert.worker.StartUp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Worker status</title>
</head>
<body>
<table border="1">
<tr><th>Component</th><th>Status</th></tr>
<tr><td>Mail settings</td><td><%=StartUp.SETTINGS.getMailSettings() %></td></tr>
<tr><td>Office used</td><td><%=StartUp.getConverterInformation().toHtml() %></tr>
<tr><td>Worker threads running:</td><td><%=StartUp.getThreadVisualisation().replace("\n", "\n<br>") %></td></tr>
</table>

</body>
</html>