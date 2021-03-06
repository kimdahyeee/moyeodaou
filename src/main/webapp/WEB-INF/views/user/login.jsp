<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<div id="login-page">
		<div class="container">
			<form class="form-login" method="post" id="validateLogin" >
				<h2 class="form-login-heading">sign in now</h2>
				<div class="login-wrap">
					<div class="has-feedback">
						<input type="text" class="form-control" placeholder="id" name="email" id="email" autofocus > <br> 
					</div>
					<div class="has-feedback">
						<input type="password" class="form-control" placeholder="Password" name="password" id="password" > <br>
					</div>
					<button class="btn btn-theme btn-block" type="submit">
						<i class="fa fa-lock"></i> 로그인
					</button>
					<hr>

					<div class="registration">
						Don't have an account yet?<br /> <a class="" href="<c:url value='/signUp'/>">Create an account</a>
					</div>
				</div>
			</form>
		</div>
	</div>
