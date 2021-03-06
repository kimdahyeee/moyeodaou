 <%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>모여다우</title>
	
	<link rel='stylesheet' href="<c:url value='/resources/css/fullcalendar.min.css'/>"  type="text/css" />
	<link rel='stylesheet' href="<c:url value='/resources/css/fullcalendar.print.css'/>" type="text/css"  media='print' />
	<script type="text/javascript" src="<c:url value='/resources/js/fullCalendar/moment.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/fullCalendar/jquery.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/fullCalendar/jquery-ui.min.js'/>"></script>
	
	<!-- Bootstrap core CSS -->
	<link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet">
	
	<!--external CSS-->
	<link href="<c:url value='/resources/font-awesome/css/font-awesome.css'/>" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/lineicons/style.css'/>">
	
	<!-- Custom styles for this template -->
	<link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
	
	<link href="<c:url value='/resources/css/style-responsive.css'/>" rel="stylesheet">
	
	<script src="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.0.2/socket.io.js'/>"></script>

</head>
<body>
   	<sec:authentication property="principal.username" var="email" />
   	<sec:authentication property="principal.memberNo" var="memberNo" />
   	<sec:authentication property="principal.memberName" var="memberName" />
	<section id="container"> 
	
	<!--header start--> 
	<header class="header black-bg">
	<div class="sidebar-toggle-box">
		<div class="fa fa-bars tooltips" data-placement="right"
			data-original-title="Toggle Navigation"></div>
	</div>
	<!--logo start--> 
	<a href="<c:url value='/main'/>" class="logo"><b>모여다우</b></a> <!--logo end-->
	<div class="nav notify-row" id="top_menu">
		<!--  notification start -->
		<ul class="nav top-menu">
			<!-- inbox dropdown start-->
			<li id="header_inbox_bar" class="dropdown">
				<a data-toggle="dropdown" class="dropdown-toggle" href="#">
					<i class="fa fa-envelope-o"></i> <span id="notify_cnt" class="badge bg-theme">0</span>
				</a>
				<ul id="chat_notify_list" class="dropdown-menu extended inbox">
					<div class="notify-arrow notify-arrow-green"></div>
					<li>
						<p class="green"><span id="notify_cnt">-</span>개의 메시지가 왔습니다.</p>
					</li>
				</ul>
			</li>
			<!-- inbox dropdown end -->
		</ul>
		<!--  notification end -->
	</div>
	<div class="top-menu">
       	<form action="<c:url value='/logout'/>" method="post">
	    	<ul class="nav pull-right top-menu">
	            <a class="main_name dont-show">${memberName} 님 </a> 
	           <li>
		            	<input type="submit" class="logout" value="Logout"/>
	           	</li>	
	    	</ul>
      	</form>
    </div>
	</header> <!--header end--> 
	
	