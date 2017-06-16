$(document).ready(function() {	
	if($("#validateLogin").length>0) {
		$("#validateLogin").validate({
			submitHandler: function(form) {   
				$.ajax({
					type: "POST",
					url: "/daou/login",
					data: {
						"email": $("#email").val(),
						"password":$("#password").val()
					},
					dataType: "json",
					success: function(data) {
						if(data.KEY=="SUCCESS") {
							location.replace(data.RETURNURI);
						} else {
							alert("회원정보를 다시 확인해 주세요.");
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
				email: {
					required: "이메일을 입력해주세요",
					email: "이메일 형식을 맞춰주세요"
				},
				password: {
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
	
	if($("#validateSignUp").length>0) {
		$("#validateSignUp").validate({
			submitHandler: function(form) {   
                $.ajax({
                    type: "POST",
                    url: "/daou/user/insertUser", 
                    data: {
                        "email": $("#email").val(),
                        "password": $("#password").val(),
                        "name": $("#name").val(),
                        "groupNo" : $("#groupNo").val(),
                        "code" : $("#code").val()
                    },
                    dataType: "json",
                    success: function(data) {
                       if(data.KEY == "USER_SUCCESS"){
                          alert("회원가입 성공");
                          location.replace("/daou/");
                       } else if(data.KEY == "EMAIL_SUCCESS") { 
                    	   alert("회원가입 & 그룹가입 성공");
                    	   location.replace("/daou/");
                       } else{
                          alert("이미 존재하는 아이디입니다.");
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
                },
                passwordConfirm: {
                    required: true,
                    minlength: 6,
                    equalTo: "#password"
                },
                name: { 
                    required: true
                }
            },
            messages: {
                email: {
                    required: "필수항목입니다.",
                    email: "이메일 형식으로 입력해주세요"
                },
                password: {
                    required: "필수항목입니다.",
                    minlength: "6글자 이상으로 입력해주세요"
                },
                passwordConfirm: {
                    required: "필수항목입니다.",
                    minlength: "6글자 이상으로 입력해주세요",
                    equalTo: "비밀번호가 일치하지 않습니다."
                },
                name: {
                    required: "필수항목입니다."
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