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
			<!-- 세개가 한 줄!!-->
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
											<td><a href="<c:url value='/group/${groupNo}/boardDetail/${allMainBoardList[status.index].board_no}'/>">${allMainBoardList[status.index].title}</a></td>
											<td>${allMainBoardList[status.index].member_name}</td>
											<td>${allMainBoardList[status.index].created_date}</td>
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
						<c:if test="${fn:length(sharing_list) > 0}"> 
							<c:forEach items="${sharing_list}" var="list" step="1" varStatus="status">
									<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
										<div class="project-wrapper" data-toggle="dropdown">
											<div class="project">
												<div class="photo-wrapper">
													<img class="img-responsive" src="../resources/img/file_img.png" alt="">
													<div class="white-header">
														<h5>${sharing_list[status.index].file_name}</h5>
														<h5>${sharing_list[status.index].member_name}</h5>
													</div>
													<div class="photo">
														<a class="fancybox" href="">
														</a>
													</div>
													<div class="overlay"></div>
												</div>
											</div>
										</div>
										<input type="hidden" id="fno" name="fno" value="${sharing_list[status.index].group_file_no}">	
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">미리보기</a></li>								
											<!--  <li><a href="<c:url value='/fileDownload?fno=${sharing_list[status.index].group_file_no}&=${_csrf.parameterName}&=${_csrf.token}'/>">다운로드</a></li>	-->
											<li><a href="<c:url value='/fileDownload?fno=${sharing_list[status.index].group_file_no}'/>">다운로드</a></li>
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
					<!-- /////////////////////////////////////// Schedule Modal //////////////////////////////////////////////////  -->
					<%-- <div class="modal fade" id="addSchedule" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">일정등록<h4>
								</div>
								<div class="modal-body">
								
								<div id="right">
									<table id="table2">
									<colgroup>
										<col width="50"/>
										<col width="100"/>
										<col width="100"/>
										<col width="100"/>
										<col width="100"/>
										<col width="100"/>
									</colgroup>
									<tbody>
									<tr>
										<!-- if checkbox is checked, clone school subjects to the whole table row  -->
										<td class="mark blank"><input id="week" type="checkbox" title="Apply school subjects to the week"/></td>
										<td class="mark dark">Monday</td>
										<td class="mark dark">Tuesday</td>
										<td class="mark dark">Wednesday</td>
										<td class="mark dark">Thursday</td>
										<td class="mark dark">Friday</td>
									</tr>
									<tr>
										<td class="mark dark">8:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="mark dark">9:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="mark dark">10:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="mark dark">11:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="mark dark">12:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="mark dark">13:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="mark dark">14:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="mark dark">15:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="mark dark">16:00</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									</tbody>
								</table>
							</div><!-- right container -->
								
								</div>
								<div class="modal-footer">
								</div>
							</div>
						</div>
					</div> --%>
					
					
					
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
						<div class="white-panel desc donut-chart">
							<div class="row">
								<div class="col-sm-6 col-xs-6 goleft">
									<p>
										<i class="fa fa-database"></i> 50%
									</p>
								</div>
							</div>
							<canvas id="serverstatus01" height="120" width="120"></canvas>
							<script>
								var doughnutData = [
										{
											value: 50,
											color:"#68dff0"
										},
										{
											value : 50,
											color : "#fdfdfd"
										}
									];
									var myDoughnut = new Chart(document.getElementById("serverstatus01").getContext("2d")).Doughnut(doughnutData);
							</script>
						</div>
						<!--/grey-panel -->
					</div>
					
					<div class="flat_item">
					<h3>현재 접속 리스트</h3>
					</div>
					<div class="mb" id="chat_form" style="display:none;">
						<div class="white-panel desc donut-chart">
							<div class="chat_member">
								<div class="chat_member_list">
									<ul id="chat_member_list"></ul>
								</div>
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
						
						<div class="chat_list">
							<strong>대화</strong><br>
							<ul id="chat_list"></ul>
						</div>
						
						<div class="chat_input">
							<input type='text' id="chat_input" class="chat_input_txt" value="대화 글을 입력하세요." onclick="$(this).val('');")/>
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

<!-- chatting script -->
<script>
	 var host = "175.115.95.51";
	// var host = "192.168.219.102";
	//var host = "172.21.21.61";
	//var host = "localhost"
	var port = "3000";
	var chat_id = "";
	var cnt = 0;
	var notify_cnt = 0;

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
	var member_list = [];
	var chat_socket = null;
	var group_socket = null;

	$(document).ready(function() {
		chat_socket = io.connect('http://'+ host + ':' + port +'/chat');
		group_socket = io.connect('http://' + host + ':' + port + '/group');
	
		init_list();
		console.log(member_list);
		
		member_list = set_member_list(member_list);
	
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
				$('#chat_member_list').append('<li>' + result[temp_keys[i]].member_name + ":"+ result[temp_keys[i]].state+'</li>');
			}

			data = decodeURIComponent(data.msg);
			data = ((data.replace(/&/g, '&amp;')).replace(/\"/g, '&quot;')).replace(/\'/g, '&#39;');
			data = data.replace(/</g, '&lt;').replace(/>/g, '&gt;');
			
			//$('#chat_list').append('<li>' + data + '</li>');
			//$('.chat_list').scrollTop($('#chat_list').height());
		});

		group_socket.on('notify', function (data) {
			var time = data.split("*")[0];
			var token = data.split("*")[1];
			var from = data.split("*")[2];
			$("#notify_cnt").html(cnt++);
			if(token != group.group_no) {
				/*$('#chat_notify_list').append('
						<li> <a href="/group">' + data + '</a> </li>
						');*/
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
			console.log(data);
			data = decodeURIComponent(data);
			data = ((data.replace(/&/g, '&amp;')).replace(/\"/g, '&quot;')).replace(/\'/g, '&#39;');
			data = data.replace(/</g, '&lt;').replace(/>/g, '&gt;');
			
			$('#chat_list').append('<li>' + data + '</li>');
			$('.chat_list').scrollTop($('#chat_list').height());
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

			//data = decodeURIComponent(data.msg);
			//$('#chat_list').append('<li>' + data + '</li>');
			//$('.chat_list').scrollTop($('#chat_list').height());
		});

		// msg input and enter key
		$('#chat_input').keyup(function (event) {
			if (event.which == 13) {
				chat_input();
			}
		});
		
	
		
	})
	(function ($) {
		'use strict';
		$('.flat_item').on("click", function() {
			$(this).next().slideToggle(100);
			$('#chat_form').not($(this).next()).slideUp('fast');
		});
	
	}(jQuery));
	
	function init_list() {
		<c:forEach items="${otherGroupList}" var="ogList">
			group_info.push({GROUP_NO: '${ogList.groupNo}' });
		</c:forEach>
		
		<c:forEach items="${groupMemberList}" var="gmList">
			member_list.push({
				MEMBER_NO: '${gmList.memberNo}',
				MEMBER_NAME: '${gmList.memberName}' 
				});
		</c:forEach>
	};

	function chat_input() {
		var encodedMsg = encodeURIComponent($('#chat_input').val());
		console.log(encodedMsg);
		chat_socket.emit('send_message', { channel: channel, member: member, message: encodedMsg });
		group_socket.emit('send_notify', { channel: channel, member: member, message: encodedMsg });
		$('#chat_input').val(''); // clear input msg in chat_input area
	};

	// current connected member and state Object
	function current_connect(memeber_list, connect_list) {
		console.log("current_connect");
		var temp_key = Object.keys(member_list);
		var mem_len = temp_key.length;
		var con_len = connect_list.length;
		var current_connected_users = new Object();

		for(var i = 0; i < mem_len; i++) {
			current_connected_users[temp_key[i]] = { member_name : member_list[temp_key[i]], state : 'disconnected' };
		}

		for(var i = 0; i < con_len; i++) {
			var result = member_list[connect_list[i]];
			if(result != undefined)  {
				current_connected_users[connect_list[i]] = { member_name : result, state : 'connected' };
			}
		}

		return current_connected_users;
	};

	// member list in same group
	function set_member_list(member_list) {
		var len = member_list.length;
		var result = [];

		for(var i = 0; i < len; i++) {
			var member = member_list;
			result[member[i].MEMBER_NO] = member[i].MEMBER_NAME;
		}
		return result;
	};
</script>

<script type="text/x-javascript">
 	var cnt = 0;
 	var progressBar = null;
 	
 	function fn_addFile(){
 		cnt++;
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
					
				}else{
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
			}, 100);
		})
	})
	
</script>