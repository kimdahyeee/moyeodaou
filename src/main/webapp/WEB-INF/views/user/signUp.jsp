 <%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	<div id="login-page">
		<div class="container">
			<form class="form-login" id="validateSignUp" method="post">
				<h2 class="form-login-heading">가입</h2>
				<div class="login-wrap">
					<c:set var="notMemberInfo" value="${notMemberInfo}"/>
					<c:choose>
						<c:when test="${!empty notMemberInfo}">
							<input type="hidden" name="code" id="code" value="${notMemberInfo.code}"/>
							<input type="hidden" name="groupNo" id="groupNo" value="${notMemberInfo.groupNo}"/>
							<div class="has-feedback">
							<input type="text" class="form-control" placeholder="User ID" name="email" id="email" value="${notMemberInfo.email}" readonly autofocus> <br> 
							</div>
						</c:when>
						<c:otherwise>
						<div class="has-feedback">
							<input type="text" class="form-control" placeholder="User ID" name="email" id="email" autofocus> 
						</div>
						<br>
						</c:otherwise>
					</c:choose>
					<div class="has-feedback">
					<input type="password" class="form-control" name="password" id="password" placeholder="비밀번호">  
					</div>
					<br>
					<div class="has-feedback">
					<input type="password" class="form-control" name="passwordConfirm" id="passwordConfirm" placeholder="비밀번호 확인"> 
					</div>
					<br> 
					<div class="has-feedback">
					<input type="text" class="form-control" name="name" id="name" placeholder="이름">
					</div><br>
					<button class="btn btn-theme btn-block" type="submit">가입하기</button>
				</div>
			</form>
		</div>
	</div>