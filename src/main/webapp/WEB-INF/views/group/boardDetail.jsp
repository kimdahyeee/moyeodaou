<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<!-- **********************************************************************************************************************************************************
    			  MAIN CONTENT
      *********************************************************************************************************************************************************** -->
	<!--main content start--> 
	<section id="main-content"> 
		<section class="wrapper">
			<h3>
				<i class="fa fa-angle-right"></i> 게시판
			</h3>
		
			<!-- BASIC FORM ELELEMNTS -->
			<div class="row mt">
				<div class="col-lg-12">
					<div class="form-panel">
						<h4 class="mb">
							<i class="fa fa-angle-right"></i> 게시글
						</h4>
						<form class="form-horizontal style-form" method="post" action="<c:url value='/group/${groupNo}/detailBoard/${ boardDetailMap.board_no}/update'/>">
							<div class="form-group">
								<label class="col-sm-2 col-sm-2 control-label">제목</label>
								<div class="col-sm-10">
									<p class="form-control-static">${ boardDetailMap.title}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 col-sm-2 control-label">작성자</label>
								<div class="col-lg-10">
									<p class="form-control-static">${boardDetailMap.member_name }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 col-sm-2 control-label">내용</label>
								<div class="col-sm-10">
									<p class="form-control-static">${ boardDetailMap.contents}</p>
								</div>
							</div>
							<c:set var="authorNO" value="${boardDetailMap.member_no}" />
							<sec:authentication property="principal.memberNo" var="currentUserNO"/>
							<c:if test="${ authorNO eq currentUserNO }">
								<a href="<c:url value='/group/${groupNo}/deleteBoard/${ boardDetailMap.board_no}'/>" class="btn btn-default btn-lg btn-block" >삭제</a>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
								<button type="submit" class="btn btn-default btn-lg btn-block">수정</button>
							</c:if>
							<a href="<c:url value='/group/${groupNo}/boardList'/>" class="btn btn-primary btn-lg btn-block">목록</a>
						</form>
					</div>
				</div>
				<!-- col-lg-12-->
			</div><!-- /row -->
		</section><!--/wrapper --> 
	</section><!-- /MAIN CONTENT -->
	