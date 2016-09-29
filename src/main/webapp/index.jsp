<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<body>
	<h2>Hello World!</h2>

	<h3>Template</h3>
	<a href="<%=basePath%>tpl/helloJsp" target="_blank">jsp</a><br />
	<a href="<%=basePath%>tpl/helloFtl" target="_blank">freemarker</a><br />
	<a href="<%=basePath%>tpl/helloVm" target="_blank">velocity</a><br />

	<h3>freemarker</h3>
	<a href="<%=basePath%>news/newsList" target="_blank">新闻列表</a><br />
	<a href="<%=basePath%>news/newsContent" target="_blank">新闻内容</a><br />

</body>
</html>
