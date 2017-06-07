 <%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>모여다우</title>
	
	<!-- Bootstrap core CSS -->
	<link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet">
	
	<!--external CSS-->
	<link href="<c:url value='/resources/font-awesome/css/font-awesome.css'/>" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/lineicons/style.css'/>">
	
	<!-- Custom styles for this template -->
	<link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
	
	<link href="<c:url value='/resources/css/style-responsive.css'/>" rel="stylesheet">
	
	<script src="<c:url value='/resources/js/chart-master/Chart.js'/>"></script>
	
	<script src="<c:url value='/resources/node_modules/socket.io-client/dist/socket.io.js'/>"></script>

</head>
<body>
   	<sec:authentication property="principal.username" var="email" />
   	<sec:authentication property="principal.memberNo" var="memberNo" />
   	<sec:authentication property="principal.memberName" var="memberName" />
	<section id="container"> <!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
	<!--header start--> 
	<header class="header black-bg">
	<div class="sidebar-toggle-box">
		<div class="fa fa-bars tooltips" data-placement="right"
			data-original-title="Toggle Navigation"></div>
	</div>
	<!--logo start--> <a href="<c:url value='/main'/>" class="logo"><b>모여다우</b></a> <!--logo end-->
	<div class="nav notify-row" id="top_menu">
		<!--  notification start -->
		<ul class="nav top-menu">
			<!-- inbox dropdown start-->
			<li id="header_inbox_bar" class="dropdown">
				<a data-toggle="dropdown" class="dropdown-toggle" href="#">
					<i class="fa fa-envelope-o"></i> <span class="badge bg-theme">5</span>
				</a>
				<ul class="dropdown-menu extended inbox">
					<div class="notify-arrow notify-arrow-green"></div>
					<li>
						<p class="green">You have 5 new messages</p>
					</li>
					<li>
						<a href="#">
							<span class="photo">
								<img alt="avatar" src="<c:url value='/resources/img/ui-zac.jpg'/>">
							</span>
							<span class="subject">
								<span class="from">Zac Snider</span>
								<span class="time">Just now</span>
							</span>
							<span class="message"> Hi mate, how is everything? </span>
						</a>
					</li>
					<li>
						<a href="#">
							<span class="photo">
								<img alt="avatar" src="<c:url value='/resources/img/ui-zac.jpg'/>">
							</span>
							<span class="subject">
								<span class="from">Zac Snider</span>
								<span class="time">Just now</span>
							</span>
							<span class="message"> Hi mate, how is everything? </span>
						</a>
					</li>
					<li>
						<a href="#">
							<span class="photo">
								<img alt="avatar" src="<c:url value='/resources/img/ui-zac.jpg'/>">
							</span>
							<span class="subject">
								<span class="from">Zac Snider</span>
								<span class="time">Just now</span>
							</span>
							<span class="message"> Hi mate, how is everything? </span>
						</a>
					</li>
					<li>
						<a href="#">
							<span class="photo">
								<img alt="avatar" src="<c:url value='/resources/img/ui-zac.jpg'/>">
								
							</span>
							<span class="subject">
								<span class="from">Zac Snider</span>
								<span class="time">Just now</span>
							</span>
							<span class="message"> Hi mate, how is everything? </span>
						</a>
					</li>
					<li>
						<a href="#">See all messages</a>
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
	    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      	</form>
    </div>
	</header> <!--header end--> 