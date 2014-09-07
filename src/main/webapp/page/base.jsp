<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	request.setAttribute("contextPath", path);
%>
<script src="${contextPath}/resource/script/jquery-1.8.0.js"></script>
<script src="${contextPath}/resource/script/jquery-1.7.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="${contextPath}/resource/style/jquery-ui.css" /> 
<script type="text/javascript" src="${contextPath}/resource/script/jquery-ui.js"></script>
<script type="text/javascript" src="${contextPath}/resource/script/jquery.ui.dialog.js"></script>


<link rel="stylesheet" href="${contextPath}/resource/style/bootstrap/bootstrap.min.css" />

  	<link rel="stylesheet" type="text/css" href="${contextPath}/resource/style/theme.css">
  	<link rel="stylesheet" type="text/css" href="${contextPath}/resource/style/font-awesome/css/font-awesome.css">
  	<link rel="stylesheet" type="text/css" href="${contextPath}/resource/style/boxy.css">
  	<link rel="stylesheet" type="text/css" href="${contextPath}/resource/style/colorbox.css">
  

  	<script src="${contextPath}/resource/script/bootstrap/bootstrap.min.js" type="text/javascript"></script>
  	<script src="${contextPath}/resource/script/bootstrap/bootstrap-paginator.js" type="text/javascript"></script>
  	<script src="${contextPath}/resource/script/page.js" type="text/javascript"></script>
  	<script src="${contextPath}/resource/script/jquery.boxy.js" type="text/javascript"></script>
  	<script src="${contextPath}/resource/script/jquery.colorbox-min.js" type="text/javascript"></script>
<!--  	<script src="${contextPath}/resource/script/jquery-ui-timepicker-addon.js" type="text/javascript"></script>-->
<!--	<script src="${contextPath}/resource/script/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>-->
	<script src="${contextPath}/resource/script/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${contextPath}/resource/script/timepicker/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
  	<style type="text/css">
      #line-chart {
          height:300px;
          width:800px;
          margin: 0px auto;
          margin-top: 1em;
      }
      .brand { font-family: georgia, serif; }
      .brand .first {
          color: #ccc;
          font-style: italic;
      }
      .brand .second {
          color: #fff;
          font-weight: bold;
      }
	.img{max-width: 40px;height:40px;width:expression(this.width > 40 ? "40px" : this.width); }
	</style>


	