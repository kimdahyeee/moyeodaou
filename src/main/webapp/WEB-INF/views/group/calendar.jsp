<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!--  fullCalendar에만 적용 -->
<script type="text/javascript"
	src="<c:url value='/resources/js/fullCalendar/moment.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/fullCalendar/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/fullCalendar/jquery-ui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/fullCalendar/fullcalendar.js'/>"></script>
<script src="<c:url value='/resources/js/fullCalendar/ko.js'/>"></script>

<section id="main-content">
	<section class="wrapper">
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
	</section>
</section>

<script type="text/javascript">
	$(document).ready(function() {
		if ($("#calendar").length > 0) {
			$('#external-events .fc-event').each(function() {

				// store data so the calendar knows to render an event upon drop
				$(this).data('event', {
					title : $.trim($(this).text()), // use the element's text as the event title
					stick : true
				// maintain when user navigates (see docs on the renderEvent method)
				});

				// make the event draggable using jQuery UI
				$(this).draggable({
					zIndex : 999,
					revert : true, // will cause the event to go back to its
					revertDuration : 0
				//  original position after the drag
				});

			});

			/* initialize the calendar
			-----------------------------------------------------------------*/

			$('#calendar').fullCalendar({
				header : {
					right : ''
				},
				defaultView : 'agendaWeek',
				editable : true,
				droppable : true, // this allows things to be dropped onto the calendar
				drop : function() {
					// is the "remove after drop" checkbox checked?
					if ($('#drop-remove').is(':checked')) {
						// if so, remove the element from the "Draggable Events" list
						$(this).remove();
					}
				},
				minTime : '6:00:00',
				slotDuration : '1:00:00',
				allDaySlot : false,
				height : "auto"
			});
		}

	});
	function fnCalendar() {
		var obj = $('#calendar').fullCalendar('clientEvents').map(function(e) {
			return {
				start : e.start,
				end : e.end
			};
		});
		for ( var k in obj) {
			console.log(k, obj[k]);
		}
		for ( var key in obj) {
			if (obj.hasOwnProperty(key)) {
				var startDate = new Date(obj[key].start);
				var endDate = new Date(obj[key].end);
				alert(startDate.getUTCDay());
				alert(startDate.getUTCHours());
				alert(endDate.getUTCHours());
			}
		}
		console.log('Item: ', JSON.stringify(obj));
	}
</script>
