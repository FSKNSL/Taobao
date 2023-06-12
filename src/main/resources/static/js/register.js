$(function(){


	var error_check_password = false;
	var error_phone = false;
	var error_check = false;

	$("#username").blur(function (){
		$.ajax({
			url: '/checkUserExists?username='+$("#username").val(),
			async: false,
			error: function(){
				alert('AJAX请求失败')
			},
			success: function(res){
				if(res == "false")
					$("#username").next().html("用户名已存在!")
				else
					$("#username").next().html('用户名可用!')
			}
		});
	})

	$('#cpassword').blur(function() {
		check_cpwd();
	});

	$('#phone').blur(function() {
		check_phone();
	});

	function check_cpwd(){
		var pass = $('#password').val();
		var cpass = $('#cpassword').val();

		if(pass!=cpass)
		{
			$('#cpassword').next().html('提示: 两次输入的密码不一致')
			$("#modal-error-info").html($("#modal-error-info").html() + "<p>两次输入的密码不一致</p>");
			$('#cpassword').next().show();
			error_check_password = true;
		}
		else
		{
			$('#cpassword').next().hide();
			error_check_password = false;
		}		
		
	}

	function check_phone(){

		var re = /^1[0-9]{10}$/;

		if(re.test($('#phone').val()))
		{
			$('#phone').next().hide();
			error_phone = false;
		}
		else
		{
			$('#phone').next().html('提示: 联系方式格式不正确,请输入11位的电话号码')
			$("#modal-error-info").html($("#modal-error-info").html() + "<p>联系方式格式不正确，请输入11位的电话号码<p>")
			$('#phone').next().show();
			error_check_password = true;
		}

	}

	$("#btn-register").click(function (){
			$("#modal-error-info").html("");
			check_cpwd();
			check_phone();

			if(error_check_password == false && error_phone == false && error_check == false)
			{
				$.ajax({
						url: '/toRegister?username=' + $("#username").val() +
							"&password=" + $("#password").val() +
							"&phone=" + $("#phone").val(),
						type: 'GET',
						error: function (data){
						},
						success: function (data) {
							$("#modal-success-info").html("注册成功!")
							$("#btn-modal-success").click()
						}
					}
				)
			}
			else $("#btn-modal-error").click();
		});
})