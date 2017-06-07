//// var host = "175.115.95.51";
//// var host = "192.168.219.102";
//var host = "localhost"
//	var port = "3003";
//var chat_id = "";
//var cnt = 0;
//
//var group = {
//		group_no: '${groupInfo.groupNo}',
//		group_name: '${groupInfo.groupName}'
//};
//
//var member = {
//		member_no: '${memberNo}',
//		member_name: '${memberName}'
//};
//
//var channel = group.group_no;
//var group_info;
//var member_list;
//var chat_socket = null;
//var group_socket = null;
//
//$(document).ready(function () {
//	chat_socket = io.connect('http://'+ host + ':' + port +'/chat');
//	group_socket = io.connect('http://' + host + ':' + port + '/group');
//	group_info = <%- JSON.stringify(groups) %>
//	member_list = <%- JSON.stringify(member_list) %>
//
//	member_list = set_member_list(member_list);
//	//Enroll Chatting info
//	chat_socket.emit('chat_join', { member: member, channel: channel });
//	group_socket.emit('group_info', { group_info: group_info });
//
//	//Succes Chatting Join
//	chat_socket.on('chat_connect', function (data) {
//		cnt = Object.keys(data.users).length;
//		$('#chat_member_list').empty();
//		var result = current_connect(member_list ,Object.keys(data.users))
//		var temp_keys = Object.keys(result);
//		var temp_len = temp_keys.length;
//
//		for(var i = 0; i < temp_len; i++) {
//			$('#chat_member_list').append('<li>' + result[temp_keys[i]].member_name + ":"+ result[temp_keys[i]].state+'</li>');
//		}
//
//		data = decodeURIComponent(data.msg);
//		data = ((data.replace(/&/g, '&amp;')).replace(/\"/g, '&quot;')).replace(/\'/g, '&#39;');
//		data = data.replace(/</g, '&lt;').replace(/>/g, '&gt;');
//		$('#chat_list').append('<li>' + data + '</li>');
//		$('.chat_list').scrollTop($('#chat_list').height());
//	});
//
//	group_socket.on('notify', function (data) {
//		var token = data.split("*")[0];
//		data = data.split("*")[1];
//		if(token != group.group_no) {
//			$('#chat_notify_list').append('<li> <a href="/group">' + data + '</a> </li>');
//			$('.chat_notify_list').scrollTop($('#chat_notify_list').height());
//		}
//	})
//
//	chat_socket.on('connected_member', function (data) {
//		cnt = Object.keys(data.users).length;
//		$("#now_member_cnt").html(cnt);
//	});
//
//	chat_socket.on('chat_log_info', function (data) {
//		data = JSON.parse(data);
//		$("#chat_logs").empty();
//		for (var i = 0; i < data.length; i++) {
//			$("#chat_logs").append('<li>' + data[i].log
//					+ '(' + data[i].date + ')' + '</li>')
//		}
//		$('.chat_history_list').scrollTop($("#chat_logs").heights());
//	});
//
//
//	chat_socket.on('chat_fail', function (data) {
//		date = JSON.parse(data);
//		alert(data + '님은 이미 접속해있습니다.');
//		location.href("/group");
//	});
//
//	chat_socket.on("receive_message", function(data) {
//		console.log(data);
//		data = decodeURIComponent(data);
//		data = ((data.replace(/&/g, '&amp;')).replace(/\"/g, '&quot;')).replace(/\'/g, '&#39;');
//		data = data.replace(/</g, '&lt;').replace(/>/g, '&gt;');
//		$('#chat_list').append('<li>' + data + '</li>');
//		$('.chat_list').scrollTop($('#chat_list').height());
//	});
//
//	chat_socket.on('member_disconnected', function (data) {
//		$("#now_member_cnt").html(--cnt);
//
//		var result = current_connect(member_list ,Object.keys(data.users))
//		var temp_keys = Object.keys(result);
//		var temp_len = temp_keys.length;
//
//		$('#chat_member_list').empty();
//		for(var i = 0; i < temp_len; i++) {
//			$('#chat_member_list').append('<li>' + result[temp_keys[i]].member_name + " : "+ result[temp_keys[i]].state+'</li>');
//		}
//
//		data = decodeURIComponent(data.msg);
//		$('#chat_list').append('<li>' + data + '</li>');
//		$('.chat_list').scrollTop($('#chat_list').height());
//	});
//
//	// msg input and enter key
//	$('#chat_input').keyup(function (event) {
//		if (event.which == 13) {
//			chat_input();
//		}
//	});
//});
//function chat_input() {
//	var encodedMsg = encodeURIComponent($('#chat_input').val());
//	console.log(encodedMsg);
//	chat_socket.emit('send_message', { channel: channel, member: member, message: encodedMsg });
//	group_socket.emit('send_notify', { channel: channel, member: member, message: encodedMsg });
//	$('#chat_input').val(''); // clear input msg in chat_input area
//};
//// current connected member and state Object
//function current_connect(memeber_list, connect_list) {
//	console.log("current_connect");
//	var temp_key = Object.keys(member_list);
//	var mem_len = temp_key.length;
//	var con_len = connect_list.length;
//	var current_connected_users = new Object();
//
//	for(var i = 0; i < mem_len; i++) {
//		current_connected_users[temp_key[i]] = { member_name : member_list[temp_key[i]], state : 'disconnected' };
//	}
//
//	for(var i = 0; i < con_len; i++) {
//		var result = member_list[connect_list[i]];
//		if(result != undefined)  {
//			current_connected_users[connect_list[i]] = { member_name : result, state : 'connected' };
//		}
//	}
//
//	return current_connected_users;
//}
//
//// member list in same group
//function set_member_list(member_list) {
//	var len = member_list.length;
//	var result = [];
//
//	for(var i = 0; i < len; i++) {
//		var member = member_list;
//		result[member[i].MEMBER_NO] = member[i].MEMBER_NAME;
//	}
//	return result;
//}