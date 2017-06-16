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
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1; //January is 0!
			var yyyy = today.getFullYear();
			var sundayDate = new Date(yyyy, mm-1, dd-today.getUTCDay());
			
			$('#calendar').fullCalendar({
				header : {
					right : ''
				},
				defaultView : 'agendaWeek',
				editable : true,
				droppable : true,
				minTime : '6:00:00',
				maxTime : '20:00:00',
				slotDuration : '1:00:00',
				allDaySlot : false,
				height : "auto",
				eventOverlap:false,
                events: function(start, end, timezone, callback) {
                	var requestInfo = {
        					groupNo : ${groupNo}, 
        					memberNo : <sec:authentication property="principal.memberNo"/>, 
        					startDate : start.format()
        			};
                	
                    $.ajax({
                        url: "<c:url value='/calendarEvent'/>",
                        type : 'post',
                        headers: { 
        	                'Accept': 'application/json',
        	                'Content-Type': 'application/json' 
        	            },
                        data : JSON.stringify(requestInfo),
                        dataType: 'json',
                        success: function(data) {
                            var events = [];
                            $(data).each(function() {
                                events.push({
                                	id: $(this).attr('id'),
                                	title: $(this).attr('title'),
                                    start: $(this).attr('start'),
                                    end: $(this).attr('end'),
                                    color: 'yellow',   // an option!
                                    textColor: 'black',
                                    editable:false
                                });
                            });
                            callback(events);
                        }
                    });
                },
                eventClick : function(event, element) {
                    var current = $(this);
                    alert("event.id" + event.id);
                    var scheduleNo = {
        					scheduleNo : event.id 
        			};
                    if(confirm('삭제하시겠어요?')){
                        $.ajax({
                            url: "<c:url value='/deleteSchedule'/>",
                            type: "POST",
                            headers: { 
            	                'Accept': 'application/json',
            	                'Content-Type': 'application/json' 
            	            },
                            dataType: 'JSON',
                            data : JSON.stringify(scheduleNo),
                            success:function(result){
                                if(result  == "1"){
                              		$('#calendar').fullCalendar('removeEvents', event.id); 
                                }
                            },
                            error: function (request, status, error) {  
                                alert('Error on deleting the event ');
                            }
                        });
                    } 
                }
			});
		}
	});
	
	function fnCalendar() {
		var obj = $('#calendar').fullCalendar('clientEvents').map(function(e) {
			return {
				id : e.id,
				start : e.start,
				end : e.end
			};
		});
		
		for ( var k in obj) {
			console.log(k, obj[k]);
		}

		if(obj.length != 0) {
			var scheduleInfo = new Array();
			
			for ( var key in obj) {
				if (obj.hasOwnProperty(key)) {
					if(obj[key].id == null) {
						var data = new Object();
						
						var startDate = new Date(obj[key].start);
						var endDate = new Date(obj[key].end);
						
						data.scheduleDay = startDate.getUTCDay();
						data.scheduleStartTime = startDate.getUTCHours();
						
						if(obj[key].end == null){
							data.scheduleFinishTime = data.scheduleStartTime + 1;
						} else{
							if(endDate.getUTCHours() == 0){
								data.scheduleFinishTime = 23;
							} else {
								data.scheduleFinishTime = endDate.getUTCHours() - 1;
							}
						}
						scheduleInfo.push(data);
					}
				}
			}
			
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1; //January is 0!
			var yyyy = today.getFullYear();
			var sundayDate = new Date(yyyy, mm-1, dd-today.getUTCDay());

			var scheduleInfos = {
					groupNo : ${groupNo}, 
					memberNo : <sec:authentication property="principal.memberNo"/>, 
					weekStartDate : sundayDate,
					scheduleInfos : scheduleInfo
			};
			
			console.log('Item: ', JSON.stringify(scheduleInfos));
			
			$.ajax({
	            type: "POST",
	            url: "/daou/insertSchedule", 
	            processData : true,
	            headers: { 
	                'Accept': 'application/json',
	                'Content-Type': 'application/json' 
	            },
	            data: JSON.stringify(scheduleInfos),
	            dataType: "json",
	            success: function(data) {
	            	alert("저장되었습니다.");
	            }
	        });
		} else {
			alert("스케줄을 입력해 주세요.")
		}
	}
</script>
