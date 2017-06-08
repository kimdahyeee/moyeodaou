<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion">
              	  <p class="centered">
              	  <a href="#"><img src="<c:url value='resources/img/ui-sam.jpg'/>" class="img-circle" width="60"></a>
              	  ${groupInfo.groupImg}
              	  </p>
              	  <h5 class="centered">${groupInfo.groupName}</h5>
              	  	
                  <li class="mt">
                      <a class="active" href="<c:url value='/group/${groupNo}'/>">
                          <i class="fa fa-dashboard"></i>
                          <span>메인화면</span>
                      </a>
                  </li>
					<sec:authorize access="hasRole('ROLE_GROUP${groupNo}_MEMBER')">
                  <li class="sub-menu">
                      <a data-toggle="modal" data-target="#leaveThisGroup" href="#">
                          <i class="fa fa-dashboard"></i>
                            <span>탈퇴</span>
                      </a>
                  </li>
                  </sec:authorize>
                  <sec:authorize access="hasRole('ROLE_GROUP${groupNo}_MASTER')">
                  <li class="sub-menu">
                      <a href="#" data-toggle="modal" data-target="#inviteGroup">
                          <i class="fa fa-dashboard"></i>
                          <span>팀원추가</span>
                      </a>
                  </li>
                  <li class="sub-menu">
                      <a href="#" data-toggle="modal" data-target="#removeThisGroup">
                          <i class="fa fa-dashboard"></i>
                          <span>그룹해체</span>
                      </a>
                  </li>
                  </sec:authorize>
                  <li class="sub-menu">
                      <a href="#" data-toggle="modal" data-target="#addSchedule">
                          <i class="fa fa-dashboard"></i>
                          <span>일정등록</span>
                      </a>
                  </li>
              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside> <!--sidebar end--> 
	<!-- 탈퇴 modal -->
	<div class="modal fade" id="leaveThisGroup" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">탈퇴</h4>
				</div>
				<div class="modal-body">정말로 탈퇴하시겠어요?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<a type="button" class="btn btn-primary" href="<c:url value='/group/${groupNo}/deleteGroupMember'/>">확인</a>
				</div>
			</div>
		</div>
	</div>

	<!-- 그룹해체 modal -->
	<div class="modal fade" id="removeThisGroup" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">그룹해체</h4>
				</div>
				<div class="modal-body">
					정말로 삭제하시겠어요?<br> (그룹원이 존재할 경우 삭제하실 수 없습니다.)
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<c:if test="${groupInfo.groupTotalCount eq 1}">
						<a type="button" class="btn btn-primary" href="<c:url value='/group/${groupNo}/deleteGroup'/>">확인</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<!-- 그룹 초대 modal -->
	<div class="modal fade" id="inviteGroup" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">그룹 초대</h4>
				</div>
				<div class="modal-body">
					<!--  <form class="form-horizontal style-form" method="post">	-->
					<form action="<c:url value='/group/${groupNo}/email'/>" class="form-horizontal style-form" method="post">
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">email주소</label>
							<div class="col-sm-10">
								<input type="email" name="receiverId" class="form-control">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
							</div>
						</div>
						<div class="centered">
							<button type="submit" class="btn btn-primary">초대</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">취소</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div> <!--sidebar end--> 
	