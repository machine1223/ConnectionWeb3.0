function load()
{
	setBtnComfirmClick();
	setBtnLoginClick();
}

function setBtnComfirmClick()
{
	$(document).ready(function(){
		$("#btnConfirm").click(function(){
			signup();
		});
	});
}

function setBtnLoginClick()
{
	$(document).ready(function(){
		$("#btnLogin").click(function(){
			login();
		});
	});
}

function signup()
{
	var name = $("#txtName").val();
	var pw = $("#txtPassword").val();
	var pwAgain = $("#txtPasswordAgain").val();
	if(pw != pwAgain)
	{
		$("#warnningMessage").text("密码不一致");
		return;
	}
	$.post(
		"SignupServlet",
		{
			name:name,
			password:pw
		},
		function(result)
		{
			if(result != "true")
				$("#warnningMessage").text("账户名已存在");
			else
			{
				alert("注册成功");
				window.location.href="login.html";
			}
		}
	);
}

function login()
{
	window.location.href="login.html";
}