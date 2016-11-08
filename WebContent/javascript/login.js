function load()
{
	setBtnComfirmClick();
	setBtnSignupClick();
	check();
}

function check()
{
	$.get(
		"LoginServlet",
		function(result)
		{
			if(result == "true")
			{
				window.location.href="main.html";
			}
		}
	);
}

function setBtnComfirmClick()
{
	$(document).ready(function(){
		$("#btnComfirm").click(function(){
			login();
		});
	});
}

function setBtnSignupClick()
{
	$(document).ready(function(){
		$("#btnSignup").click(function(){
			signup();
		});
	});
}

function login()
{
	var name = $("#txtName").val();
	var password = $("#txtPassword").val();
	$.post(
		"LoginServlet",
		{
			name:name,
			password:password
		},
		function(result)
		{
			if(result == "false")
			{
				$("#warnningMessage").text("账户名不存在或者密码错误");
			}
			else if(result == "true")
			{
				alert("登陆成功");
				window.location.href="main.html";
			}
		}
	);
}

function signup()
{
	window.location.href="signup.html";
}