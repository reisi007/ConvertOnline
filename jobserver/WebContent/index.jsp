<%@page import="at.reisisoft.convert.server.StartUp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Management UI</title>
</head>
<body>
<h1>Job server running!</h1>

Currently there are <span style="font-weight: bold;"><%=StartUp.getQueueJobCount() %></span> jobs in the queue!
</body>
</html>