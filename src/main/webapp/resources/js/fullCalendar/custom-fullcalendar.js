$(document).ready(function() {
		if ($("#calendar").length > 0) {
			$('#external-events .fc-event').each(function() {
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
				droppable : true,
				minTime : '6:00:00',
				maxTime : '20:00:00',
				slotDuration : '1:00:00',
				allDaySlot : false,
				height : "auto",
				eventOverlap:false,
                events: function(start, end, timezone, callback) {
                	var requestInfo = {
        					groupNo : $("#groupNo").val(), 
        					memberNo : $("#memberNo").val(),
        					startDate : start.format()
        			};
                	
                    $.ajax({
                        url: "/daou/calendarEvent",
                        type : "POST",
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
	                    var scheduleUserInfo = {
	                    		groupNo : $("#groupNo").val(), 
	        					scheduleNo : event.id 
	        			};

	                    if(confirm('삭제하시겠어요?')){
	                        $.ajax({
	                            url: "/daou/deleteSchedule",
	                            type: "POST",
	                            headers: { 
	            	                'Accept': 'application/json',
	            	                'Content-Type': 'application/json' 
	            	            },
	                            dataType: 'JSON',
	                            data : JSON.stringify(scheduleUserInfo),
	                            success:function(result){
	                                if(result  == "1"){
	                              		$('#calendar').fullCalendar('removeEvents', event.id); 
	                                } else {
	                                	alert('실패');
	                                }
	                            },
	                            error: function (request, status, error) {  
	                                alert('error : ' + error);
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
			var mm = today.getMonth(); 
			var yyyy = today.getFullYear();
			var sundayDate = new Date(yyyy, mm, dd-today.getDay());

			console.log(sundayDate);
			var scheduleInfos = {
					groupNo : $("#groupNo").val(), 
					memberNo : $("#memberNo").val(),
					weekStartDate : sundayDate,
					scheduleInfos : scheduleInfo
			};
			
			$.ajax({
	            type: "POST",
	            url: "/daou/insertSchedule", 
	            headers: { 
	                'Accept': 'application/json',
	                'Content-Type': 'application/json' 
	            },
	            data: JSON.stringify(scheduleInfos),
	            dataType: "json",
	            success: function(data) {
	            	alert("저장되었습니다."); 
	            	loadEvents();
	            }
	        });
		} else {
			alert("스케줄을 입력해 주세요.");
		}
	}
	
	function loadEvents() {
		$('#calendar').fullCalendar( 'removeEvents');
    	$('#calendar').fullCalendar( 'refetchEvents' );
	}
	