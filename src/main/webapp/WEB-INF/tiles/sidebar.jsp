<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
$(document).ready(function() {

	/* initialize the external events
	-----------------------------------------------------------------*/

	$('#external-events .fc-event').each(function() {

		// store data so the calendar knows to render an event upon drop
		$(this).data('event', {
			title: $.trim($(this).text()), // use the element's text as the event title
			stick: true // maintain when user navigates (see docs on the renderEvent method)
		});

		// make the event draggable using jQuery UI
		$(this).draggable({
			zIndex: 999,
			revert: true,      // will cause the event to go back to its
			revertDuration: 0  //  original position after the drag
		});

	});


	/* initialize the calendar
	-----------------------------------------------------------------*/

	$('#calendar').fullCalendar({
		header: {
			right: ''
		},
        defaultView: 'agendaWeek',
		editable: true,
		droppable: true, // this allows things to be dropped onto the calendar
		drop: function() {
			// is the "remove after drop" checkbox checked?
			if ($('#drop-remove').is(':checked')) {
				// if so, remove the element from the "Draggable Events" list
				$(this).remove();
			}
		},
        minTime:'6:00:00',
        slotDuration: '1:00:00',
        allDaySlot : false,
        height: "auto"
	});


});
        function fnCalendar(){
            var obj = $('#calendar').fullCalendar('clientEvents').map(function(e) {
                return {
                    start: e.start,
                    end: e.end
                };
            });
            console.log('Item: ', JSON.stringify(obj));
        }
    
    </script>
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
<body>
<div class="modal fade" id="addSchedule" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">일정 추가</h4>
			</div>
			<div class="modal-body">
				<div id="wrap">
					<div id="external-events">
						<h4>드래그하여 일정을 추가해주세요.</h4>
						<div class="fc-event">일정 추가</div>
						<p>
							<input type="checkbox" id="drop-remove" /> <label
								for="drop-remove">remove after drop</label>
						</p>
					</div>
					<div id="calendar"></div>
					<input type="button" onclick="javascript:fnCalendar();" value="입력" />
					<div style="clear: both"></div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
