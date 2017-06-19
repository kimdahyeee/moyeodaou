<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<!-- **********************************************************************************************************************************************************
    			  MAIN CONTENT
      *********************************************************************************************************************************************************** -->
	<!--main content start-->
	<section id="main-content">
		<section class="wrapper">
			<div class="row">
				<div class="col-md-9 mt">
					<div class="content-panel">
						<table class="table table-hover">
							<h4>
								<i class="fa fa-angle-right"></i> 게시판
								<i class="fa fa-angle-right more"><a href="<c:url value='/group/${groupNo}/boardWrite'/>">게시글 쓰기</a></i> 
								<i class="fa fa-angle-right more"><a href="<c:url value='/group/${groupNo}/boardList'/>"> 더보기</a></i>
							</h4>
							<hr>
							<thead>
								<tr>
									<th>#</th>
									<th>제목</th>
									<th>작성자</th>
									<th>게시일</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(allMainBoardList) > 0 }">
									<c:forEach var="allMainBoardLists" items="${allMainBoardList}" begin="0" end="${fn:length(allMainBoardList)}" step="1" varStatus="status">
										<tr>
											<td>${allMainBoardList[status.index].rNum}</td>
											<td><a href="<c:url value='/group/${groupNo}/boardDetail/${allMainBoardList[status.index].boardNo}'/>">${allMainBoardList[status.index].title}</a></td>
											<td>${allMainBoardList[status.index].memberName}</td>
											<td>${allMainBoardList[status.index].createdDate}</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>

					<div class="row mt">
						<!-- SERVER STATUS PANELS -->
						<!-- /col-md-4-->
						<div class="col-lg-4 col-md-4 col-sm-4 desc">
							<div class="content-panel pn">
								<div id="profile-01">
									<h3></h3>
								</div>
								<div class="profile-01 centered">
									<a data-toggle="modal" data-target="#fileUpload" href="#">
					                    	<p>추가</p>
					                </a>
								</div>
								<div class="centered">
									<h6>
										<i class="fa fa-heart"></i><br />게시물을 추가해 주세요!
									</h6>
								</div>
							</div>
							<!--/content-panel -->
						</div>
						<!--/col-md-4 -->
						<c:if test="${fn:length(sharingList) > 0}"> 
							<c:forEach items="${sharingList}" var="list" step="1" varStatus="status">
									<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
										<div class="project-wrapper" data-toggle="dropdown">
											<div class="project">
												<div class="photo-wrapper">
													<img class="img-responsive" src="../resources/img/file_img.png" alt="">
													<div class="white-header">
														<h5>${sharingList[status.index].fileName}</h5>
														<h5>${sharingList[status.index].memberName}</h5>
													</div>
													<div class="photo">
														<a class="fancybox" href="">
														</a>
													</div>
													<div class="overlay"></div>
												</div>
											</div>
										</div>
										<input type="hidden" id="fno" name="fno" value="${sharingList[status.index].group_file_no}">	
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">미리보기</a></li>								
											<!--  <li><a href="<c:url value='/fileDownload?fno=${sharing_list[status.index].group_file_no}&=${_csrf.parameterName}&=${_csrf.token}'/>">다운로드</a></li>	-->
											<li><a href="<c:url value='/fileDownload?fno=${sharingList[status.index].groupFileNo}'/>">다운로드</a></li>
											<li><a href="#">삭제</a></li>
										</ul>
									</div>
									<br/>
								<!--  </form> -->
							</c:forEach>
						</c:if>
						<!-- col-lg-4 -->
					</div>
					<!-- 파일 업로드 모달 창 -->
					<div class="modal fade" id="fileUpload" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
							<form:form action="/daou/group/${groupNo}/fileUpload" id="fileUpload" name="fileUpload" method="post" enctype="multipart/form-data">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">파일 업로드</h4>
								</div>
								<div class="modal-body">
									<input type="button" value="+" id="addFile">
									<button type="submit" id="uploadButton">완료</button>
									<br>
									<div id="progressImg" style="width:0%; background-color: red;"></div>전송상황
			 					</div>
								<div class="modal-footer" id="fileUpload-footer">
										<a href="#this"	id="fileUpload2"></a>
										<!--  
										<form:form action="/daou/group/${groupNo}/fileUpload" id="fileUpload" name="fileUpload" method="post" enctype="multipart/form-data">
											<input type="file" name="testFile" required="required"/>		
											<input type="submit" value="완료"/>
									 	</form:form>
									 	-->
								</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
				<!-- /col-lg-9 END SECTION MIDDLE -->


				<!-- **********************************************************************************************************************************************************
     RIGHT SIDEBAR CONTENT
     *********************************************************************************************************************************************************** -->
				<div class="col-lg-3 ds">

					<!--COMPLETED ACTIONS DONUTS CHART-->
					<div class="flat_item">
					<h3>회의 가능 시간</h3>
					</div>
					<div class="mb" id="chat_form" style="display:none;">
							<div class="current_added_list">
							<strong>현재 등록 리스트</strong>
							<ul id="current_added_list">
							</ul>
							</div>
						<div class="white-panel desc donut-chart">
							<div id="available_date">
							</div>
						</div>
						<!--/grey-panel -->
					</div>

					<!-- USERS ONLINE SECTION -->
					<div class="flat_item">
					<h3>
					채팅(접속자 수 :<span id="now_member_cnt">-</span>)
					</h3>
					</div>
					<div id="chat_form" class="desc chat_form">
						<div class="chat_notify_list">
							<ul id="chat_notify_list"></ul>
						</div>
					<div class="chat_member_list">
						<strong>현재 접속 리스트</strong>
						<ul id="chat_member_list">
						</ul>
					</div>

					<div class="chat_list" >
						<strong>대화</strong><br>
						<div id="chat_scroll" style="overflow:auto; height:400px;">
							<div class="centered"> 
								<a id="more" href="javascript:more_history();" class="centered"><b>채팅 기록 더보기</b></a>
							</div>
							<ul id="chat_list"> </ul>
						</div>
					</div>
						
					<div class="chat_input" >
						<input type='text' size="50" id="chat_input" class="chat_input_txt" value="대화 글을 입력하세요." onclick="$(this).val('');")/>
					</div>
					
					<div class="chat_btn">
						<a href="javascript:chat_input();">입력</a>
					</div>
					</div>
				</div>
				<!-- /col-lg-3 -->
			</div>
			<!-- /row -->
		</section>
</section>

<!-- 
	title : chat client script
	author : Daeho Han
	-->
<script>
	var host = "175.115.95.51";
	var port = "3000";
	var cnt = 0;
	var notify_cnt = 0;
	var more_cnt = 0;
	var temp_id = null;

	var group = {
		group_no: '${groupInfo.groupNo}',
		group_name: '${groupInfo.groupName}'
	};

	var member = {
		member_no: '${memberNo}',
		member_name: '${memberName}'
	};
	
	var group_info = [];
	var channel = group.group_no;
	var member_list = new Object();
	var chat_socket = null;
	var group_socket = null;
	var history_date = null;

	$(document).ready(function() {
		chat_socket = io.connect('http://'+ host + ':' + port +'/chat');
		group_socket = io.connect('http://' + host + ':' + port + '/group');
	
		init_list();
		console.log(member_list);
		
		
		//Enroll Chatting info
		chat_socket.emit('chat_join', { member: member, channel: channel });
		group_socket.emit('group_info', { group_info: group_info });
		

		//Succes Chatting Join
		chat_socket.on('chat_connect', function (data) {
			cnt = Object.keys(data.users).length;
			$('#chat_member_list').empty();
			
			var result = current_connect(member_list ,Object.keys(data.users))
			var temp_keys = Object.keys(result);
			var temp_len = temp_keys.length;

			for(var i = 0; i < temp_len; i++) {
				var state;
				if(result[temp_keys[i]].state == 'disconnected') {
					state = '<span style="color:red;"><b>부재중</b></span>';
				} else {
					state = '<span style="color:green;"><b>접속중</b></span>';
				}
				$('#chat_member_list').append('<li>' + result[temp_keys[i]].member_name + " : "+ state +'</li>');
			}

			data = decodeURI(data.msg);
			
			//$('#chat_list').append('<li>' + data + '</li>');
			//$('.chat_list').scrollTop($('#chat_list').height());
		});
		
        chat_socket.on('history', function(data) {
        	append_contents(data);
			$('#chat_scroll').scrollTop($('#chat_list').height());
        });
        
        chat_socket.on('load_more_history', function(data) {
        	before_contents(data);
        });

		group_socket.on('notify', function (data) {
			var time = data.split("*")[0];
			var token = data.split("*")[1];
			var from = data.split("*")[2];
			
			$("#notify_cnt").html(cnt++);
			if(token != group.group_no) {
				$('#chat_notify_list').append(' <li> <a href="#"> <span class="subject"> <span class="from">'+ from +'</span> <span class="time">'+ time +'</span> </span> <span class="message"> '+ token +'방에서 알림이 왔습니다. </span> </a> </li> ');
				$('.chat_notify_list').scrollTop($('#chat_notify_list').height());
			}
		});

		chat_socket.on('connected_member', function (data) {
			cnt = Object.keys(data.users).length;
			$("#now_member_cnt").html(cnt);
		});
	
		chat_socket.on('chat_log_info', function (data) {
			data = JSON.parse(data);
			$("#chat_logs").empty();
			for (var i = 0; i < data.length; i++) {
				$("#chat_logs").append('<li>' + data[i].log
					+ '(' + data[i].date + ')' + '</li>')
			}
			
			$('.chat_history_list').scrollTop($("#chat_logs").heights());
		});

		chat_socket.on('chat_fail', function (data) {
			date = JSON.parse(data);
			alert(data + '님은 이미 접속해있습니다.');
			location.href("/group");
		});

		chat_socket.on("receive_message", function(data) {
			data = decodeURI(data);
			var member_name = data.split(":")[0];
			var msg = data.split(":")[1];
			
			member_name = trim(member_name);
			member_name = check_me(member_name);
			
			$('#chat_list').append('<li>' + member_name + " : " + msg + '</li>');
			$('#chat_scroll').scrollTop($('#chat_list').height());
		});

		chat_socket.on('member_disconnected', function (data) {
			$("#now_member_cnt").html(--cnt);

			var result = current_connect(member_list ,Object.keys(data.users))
			var temp_keys = Object.keys(result);
			var temp_len = temp_keys.length;

			$('#chat_member_list').empty();
		
			for(var i = 0; i < temp_len; i++) {
				$('#chat_member_list').append('<li>' + result[temp_keys[i]].member_name + " : "+ result[temp_keys[i]].state+'</li>');
			}

			//data = decodeURI(data.msg);
			//$('#chat_list').append('<li>' + data + '</li>');
			//$('.chat_list').scrollTop($('#chat_list').height());
		});

		// msg input and enter key
		// * change event from keyup to keypress for IE issue (2017.06.15)
		$('#chat_input').keypress(function (event) {
			if (event.which == 13) {
				chat_input();
			} 
		});
		
		//flick 
		'use strict';
		$('.flat_item').on("click", function() {
			$(this).next().slideToggle(100);
			$('#chat_form').not($(this).next()).slideUp('fast');
		});
	
	});
	
	function more_history() {
		chat_socket.emit("more_history", { channel: channel, history_date : history_date });
	}
	
	function init_list() {
		<c:forEach items="${otherGroupList}" var="ogList">
			group_info.push({GROUP_NO: '${ogList.groupNo}' });
		</c:forEach>
		
		<c:forEach items="${groupMemberList}" var="gmList">
		console.log('${gmList.memberNo}');
			member_list['${gmList.memberNo}'] = {
				MEMBER_NO: '${gmList.memberNo}',
				MEMBER_NAME: '${gmList.memberName}' 
				};
		</c:forEach>
	};
	
	function check_me (input_name) {
		var temp_name = trim(member.member_name);
		input_name = trim(input_name);
		
		if(temp_name == input_name) {
			return '<b style="color:blue;">'+ input_name + '</b>';
		} else {
			return input_name;
		}
	}
	
	function trim(stringToTrim) {
	    return stringToTrim.replace(/^\s+|\s+$/g,"");
	}	
        
	function append_contents(data) {
		var len = data.length-1;
		
		for(var i = len; i >= 0 ; i--) {
			var contents = decodeURI(data[i].CHAT_CONTENTS);
			var member_name = check_me(data[i].MEMBER_NAME);
			var msg = member_name + ":" + contents;
			
			if(i == len) {
				$("#chat_list").append('<span class="divider" id="divider"></span>')
				$('#chat_list').append('<li id="msg">' + msg +'</li>'); 
				history_date = data[i].SEND_TIME;
			} else {
				$('#chat_list').append('<li id="msg">' + msg +'</li>');
			}
		}
	}
	
	function before_contents(data) {
		var len = data.length-1;
		
		if(len == -1) {
			alert("더 이상 불러올 기록이 없습니다");
		} else {
			for(var i = len; i >= 0 ; i--) {
				var contents = decodeURI(data[i].CHAT_CONTENTS);
				var member_name = check_me(data[i].MEMBER_NAME);
				var msg = member_name + ":" + contents;
				
				if(i == len) {
					$("[id='divider']").eq(more_cnt).html('==========이전 대화 목록==========');
					$("[id='divider']").eq(more_cnt).before('<span class="divider" id="divider"></span>')
					$("[id='divider']").eq(more_cnt+1).before('<li>' + msg + '</li>'); 
					history_date = data[i].SEND_TIME;
				} else {
					$("[id='divider']").eq(more_cnt+1).before('<li>' + msg + '</li>'); 
				}
			}
		}
	}
	

	function chat_input() {
		var encodedMsg = encodeURI($('#chat_input').val());
		$('#chat_input').val(''); // clear input msg in chat_input area
		chat_socket.emit('send_message', { channel: channel, member: member, message: encodedMsg });
		group_socket.emit('send_notify', { channel: channel, member: member, message: encodedMsg });
	};

	// current connected member and state Object
	function current_connect(memeber_list, connect_list) {
		var con_len = connect_list.length;
		var current_connected_users = new Object();
		
		$.each(member_list, function(index, value) {
			current_connected_users[index] = { member_name : value.MEMBER_NAME, state : 'disconnected' };
			for(var i = 0; i < con_len; i++) {
				if(index == connect_list[i]) {
					current_connected_users[index].state = 'connected';
				}
			}
		});

		return current_connected_users;
	};

</script>

<script type="text/x-javascript">
 	var cnt = 0;
 	var progressBar = null;
 	
 	function fn_addFile(){
 		cnt++
 		console.log(cnt);
 		var str = "<p>" +
 			"<input type='file' id='file_"+(cnt)+"' name='file_"+(cnt)+"' required='required'>"+
 			//"<input type='button' value='삭제' class='deleteFile'>" +
 			"<a class='delete' id='deletebtn' name='deleteFile'>"+'삭제'+cnt+"</a>"+ 
 			"</p>";
 		$("#fileUpload-footer").append(str);
 		console.log('end');
 		
		$(".delete").on("click", function(e){ 						// 삭제 버튼
			e.preventDefault();
			fn_deleteFile($(this));
		});
 	}
 		
 	function fn_deleteFile(obj){
 	         obj.parent().remove();
 	}
 	
 	function fn_getProgressInfo(){								// 프로그래스 바 정보 얻기
 		$.ajax({
			url:"/daou/fileUpload/progress", 
			dataType : "json",
			method: "post",
			success: function(result){
				//alert(+ result.pByteRead + " / " + result.pContentLength);
				console.log(+ result.pByteRead + " / " + result.pContentLength);
				if(result.pByteRead < result.pContentLength){
					// 프로그래스바 진행상황 보여주기
					console.log((result.pByteRead / result.pContentLength) * 100 );
					$("#progressImg").css("width", (result.pByteRead / result.pContentLength) * 100 +"%");
					$("#progressImg").html("전송중");
					
				}else{
					console.log((result.pByteRead / result.pContentLength) * 100 );
					clearInterval(progressBar);
					//alert("끝");
				}
			}
		});
 	}
	$("#addFile").on("click", function(e){ 						//파일 추가 버튼
		e.preventDefault();
		fn_addFile();
	});
	
	$(document).ready(function() {
		<!-- 프로그래스 바 요청 -->
		$('#uploadButton').click(function(){
			console.log('uploadButton!!!!!');
			progressBar = setInterval(function() {
			 	   fn_getProgressInfo();
			}, 10);
		})
	})
	
</script>

<!--  
	title : available date client script
	author : Daeho Han
 -->
<script>
	var day = ['SUN', 'MON', "TUE", "WED", "THU", "FRI", "SAT"];
	var week = [];
	var result = new Object();
	var added_member_list = [];
	
	$(document).ready(function() {
		initAvailableDate();
		init_schedule_member();
		parseDateInfo(week);
		$("#available_date").html("<b style='color:black;'>"+displayAvailableDate() + "</b>");
		append_added_member_list(member_list, added_member_list);
	});
	
	function parseDateInfo(week) {
		var len = week.length;
		var temp;
		for(var i = 0; i < len; i++) {
			temp = week[i].split(";");
			result[day[i]] = temp;
		}
	}
	
	
	function init_schedule_member() {
		<c:forEach items="${addedScheduleMemberList}" var="asmList">
			added_member_list.push('${asmList.memberNo}');
		</c:forEach>

	}
	
	function initAvailableDate() {
		<c:forEach var="entry" items="${availableDate}" varStatus="status">
			week.push('${entry.value}');
		</c:forEach>
	}
	
	function current_added_schedule(member_list, added_schedule_list)  {
		var current_added_member = new Object();
		var len = added_schedule_list.length;
		
		$.each(member_list, function(index, value) {
			current_added_member[index] = { member_name : value.MEMBER_NAME, state : 'no'};
			for(var i = 0; i < len; i++) {
				if(index == added_schedule_list[i]) {
					current_added_member[index].state = 'yes';
				}
			}
		});
		
		return current_added_member;
	};
	
	function append_added_member_list(member_list, added_member_list) {
		var current_added_member = current_added_schedule(member_list, added_member_list); 
		
		$.each(current_added_member, function (index, value){
			var state;
			if(value.state == "no") {	
				state = '<span style="color:red;"><b>미등록<b></span>';
			} else {
				state = '<span style="color:green;"><b>등록완료<b></span>';
				
			}
			$('#current_added_list').append('<li>' + current_added_member[index].member_name + " : "+ state +'</li>');
		});
	}
	
	function displayAvailableDate() {
		var tag = "";
		$.each(result, function(index, value) {
			var len = (value.length-1)
			tag += index + ":";
			for(var i = 0; i < len; i++) {
				var temp = value[i].split('-')[1];
				if(temp == 0) {
					value[i] = value[i].split('-')[0] + '-' + (Number(value[i].split('-')[0]) + 1);
				}
				tag += value[i];
				if(i < len -1) {
					tag += ", ";
				}
			}
			tag += '<br>';
		});
		
		return tag;
	}
</script>