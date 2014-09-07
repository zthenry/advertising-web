<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>广告管理后台</title>
		<script type="text/javascript">
      		var operations = '${operations}';
      		var modules = '${modules}';

    	</script>
	</head>

<frameset rows="38,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="${pageContext['request'].contextPath}/page/top.jsp" name="topFrame" scrolling="no" noresize="noresize" id="topFrame"/>
  <frameset cols="240, *"  rows="100%, *" id="frame">
      <frame src="${pageContext['request'].contextPath}/page/left.jsp" id="leftFrame" name="leftFrame" noresize="noresize" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" target="main" />
      <frame src="${pageContext['request'].contextPath}/page/right.jsp" id="mainFrame" name="mainFrame" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" target="_self" />
    </frameset>
  </frameset>
  <noframes>
  </noframes>
</html>
