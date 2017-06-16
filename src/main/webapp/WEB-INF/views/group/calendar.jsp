<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!--  fullCalendar에만 적용 -->
<script type="text/javascript"
	src="<c:url value='/resources/js/fullCalendar/fullcalendar.js'/>"></script>
<script src="<c:url value='/resources/js/fullCalendar/ko.js'/>"></script>

<section id="main-content">
	<section class="wrapper">
		<div id="wrap">
			<div id="external-events">
				<h4>드래그하여 일정을 추가해주세요.</h4>
				<div class="fc-event">일정 추가</div>
				<h5>등록된 일정은 노랑(클릭 시 삭제가능)</h5>
			</div>
			<div id="calendar"></div>
			<div style="clear: both"></div>
			<button type="button" class="btn btn-primary mt col-md-2 col-md-offset-5" onclick="javascript:fnCalendar();"> 등록 </button>
		</div>
		<input type="hidden" value="${groupNo }" id="groupNo"/>
		<input type="hidden" value="<sec:authentication property="principal.memberNo"/>" id="memberNo"/>
	</section>
</section>

<script src="<c:url value='/resources/js/fullCalendar/custom-fullcalendar.js'/>"></script>
