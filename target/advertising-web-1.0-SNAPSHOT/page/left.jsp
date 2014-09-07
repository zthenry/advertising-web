<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="/page/base.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
	    	var operationList = eval("(" + parent.operations + ")");
        	var manageItemList = eval("(" + parent.modules + ")");
        	
        	$.each(manageItemList, function(i) {
        		$(".sidebar-nav").append('<a href="#item'+i+'" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>'+this.name+'</a>');
        		var li = '';
        		var id=this.id;
        		$.each(operationList, function(j) {
        			if(id==this.moduleId){
        				li=li+'<li><a href="${contextPath}'+this.url+'" target="mainFrame">'+this.name+'</a></li>';
        			}
        		});
        		$(".sidebar-nav").append('<ul id="item'+i+'" class="nav nav-list collapse in">'+li+'</ul>');
        		
        	});
        	
		});
	</script>
</head>
  <body class=""> 

	<div class="sidebar-nav">
    </div>
    
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>
    </body>
</html>