$(document).ready(function() {	
	if($("#loginValidate").length>0) {
        $("#loginValidate").validate({
            submitHandler: function(form) {   
                // 데이터 베이스에 저장 ajax 사용
                $.ajax({
                   type: "POST",
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
                    required: true
                }
            },
            messages: {
            	email: {
                    required: "아이디를 입력해주세요",
                    email: "이메일 형식으로 입력해주세요"
                },
                password: {
                    required: "이메일을 입력해주세요"
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