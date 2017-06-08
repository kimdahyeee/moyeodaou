 <%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	<div id="login-page">
		<div class="container">
			<form class="form-login" action="<c:url value='/user/insertUser'/>" method="post">
				<h2 class="form-login-heading">가입</h2>
				<div class="login-wrap">
					<c:set var="notMemberInfo" value="${notMemberInfo}"/>
					<c:choose>
						<c:when test="${!empty notMemberInfo}">
							<input type="hidden" name="code" value="${notMemberInfo.code}"/>
							<input type="hidden" name="groupNo" value="${notMemberInfo.groupNo}"/>
							<input type="text" class="form-control" placeholder="User ID" name="email" value="${notMemberInfo.email}" readonly autofocus> <br> 
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" placeholder="User ID" name="email" autofocus> <br> 
						</c:otherwise>
					</c:choose>
					<input type="password" class="form-control" name="password" placeholder="비밀번호"> <br> 
					<input type="password" class="form-control" placeholder="비밀번호 확인"> <br> 
					<input type="text" class="form-control" name="name" placeholder="이름">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					<br>
					<button class="btn btn-theme btn-block" type="submit">가입하기</button>
				</div>
			</form>
		</div>
	</div>