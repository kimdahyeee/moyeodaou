$(document).ready(function() {	
	alert("여긴 들어오니..?");
	if($("#validateLogin").length>0) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$("#validateLogin").validate({
			submitHandler: function(form) {   
				alert("타썹");
				$.ajax({
					type: "POST",
					beforeSend:function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					url: "/login",
					data: {
						"email": $("#email").val(),
						"password":$("#password").val()
					},
					dataType: "json",
					success: function(data) {
						//성공 시 데이터 처리 
						if(data.KEY=="SUCCESS") {
							//alert(data.RETURNURI);
							location.replace(data.RETURNURI);
						} else {
							alert("로그인 실패");
						}
					}
				});
			},
			errorPlacement: function(error, element) {  
				error.appendTo(element.parent());  
			},
			onkeyup: false,
			onclick: false,
			rules: {
				email: {
					required: true,
					email: true
				},
				password: {
					required: true,
					minlength: 6
				}
			},
			messages: {
				userEmail: {
					required: "이메일을 입력해주세요",
					email: "이메일 형식을 맞춰주세요"
				},
				userPassword: {
					required: "패스워드를 입력해주세요",
					minlength: "최소 6자 이상입력해주세요"
				}
			},
			errorElement: "span",
			highlight: function (element) {
				$(element).parent().removeClass("has-success").addClass("has-error");
				$(element).siblings("label").addClass("hide");
			},
			success: function (element) {
				$(element).parent().removeClass("has-error").addClass("has-success");
				$(element).siblings("label").removeClass("hide");
			}
		});
	}
});