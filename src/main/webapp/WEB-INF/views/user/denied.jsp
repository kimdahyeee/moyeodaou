<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<div id="login-page">
		<div class="container">
			<form class="form-login" >
				<h2 class="form-login-heading">접근 할 수 없습니다.</h2>
				<div class="login-wrap">
					<a class="btn btn-theme btn-block" href="<c:url value='/main'/>">메인화면으로 이동</a>
				</div>
			</form>
		</div>
	</div>
