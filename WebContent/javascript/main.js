//var height = window.screen.availHeight;
var height =window.innerHeight;
//var width = window.screen.availWidth;
var width = window.innerWidth;

// var webSocket = new WebSocket('ws://192.168.1.101:8080/ConnectionWeb3.0/ConnectionWebSocket');
var webSocket = new WebSocket('ws://192.168.2.105:8080/ConnectionWebSocket');

//加载网页时调用的函数
function load()
{
	setContentBlock();
	setOnlineMessage();
	setNoticeBlock();
	initializeMessage();
	setBtnSend();
	setBtnDestroy();
	setBtnRefresh();
	setNoticeBlockClick();
	setWebSocket();
}

//设置contentBlock
function setContentBlock()
{
	$("#contentBlock").css(
		{
			height:height * 0.5
		});
}

//设置onlineMessage
function setOnlineMessage()
{
	$("#onlineMessage").css({
		height:height*0.4
	});
}

//设置noticeBlock
function setNoticeBlock()
{
	$("#noticeBlock").css({
		left:width-300,
		top:height-100
	});
}

//设置btnRefreshOnlineMessage
function setBtnRefresh()
{
	$(document).ready(function(){
		$("#btnRefreshOnlineMessage").click(function(){
			$.get(
					"UserMessageServlet",
					function(result)
					{
						//alert(decodeURI(result));
						processUserMessage(decodeURI(result));
					}
				);
		});
	});
}

//============================================================
//设置btnDestroy
function setBtnDestroy()
{
	$(document).ready(function(){
		$("#btnDestroy").click(function(){
			destroy();
		});
	});
}

function destroy()
{
	$.get(
		"DestroyServlet",
		function()
		{
			window.location.href="login.html";
		}
	);
}
//============================================================

//初始化个人信息
function initializeMessage()
{
	$.get(
		"UserMessageServlet",
		function(result)
		{
			//alert(result);
			processUserMessage(decodeURI(result));
		}
	);
}

//设置websocket
function setWebSocket()
{
	webSocket.onerror = function(event) {
		onError(event);
	};

	webSocket.onopen = function(event) {
		onOpen(event);
	};

	webSocket.onmessage = function(event) {
		onMessage(event);
	};
}

function onError(event)
{
	
}

function onOpen(event)
{
	
}

function onMessage(event)
{
	//alert(decodeURI(event.data));
	processConnectionMessage(event.data);
}

/*
 * receive-data-format:
 * key>data!
 * key>data!
 * key>data1-data2!
 * ...
 * key>name!
 * key>onlineAccount!
 * key>onlineNumber!
 * key>connectiongName!
 * key>orignName!
 * key>targetName!
 * key>content!
 * */

//处理服务器传来的用户信息
function processUserMessage(data)
{
	data = decodeURI(data);
	data = data.split("!");
	for(var i = 0;i < data.length;i++)
	{
		var temp = data[i].split(">");
		if(temp[0] == "name")
		{
			$("#myName").text(temp[1]);
		}
		else if(temp[0] == "onlineAccount")
		{
			processOnlineAccount(temp[1]);
		}
		else if(temp[0] == "onlineNumber")
		{
			$("#onlineNumber").text(temp[1]);
		}
	}
}

//处理服务器传来的聊天信息
function processConnectionMessage(data)
{
	data = decodeURI(data);
	data = data.split("!");
	var orignName = "";
	var content = "";
	var targetName = "";
	for(var i = 0;i < data.length;i++)
	{
		var temp = data[i].split(">");
		if(temp[0] == "orignName")
		{
			orignName = temp[1];
		}
		else if(temp[0] == "targetName")
		{
			targetName = temp[1];
		}
		else if(temp[0] == "content")
		{
			content = temp[1];
		}
	}
	var myName = $("#myName").text();
	var connectingName  = $("#connectiongName").text();
	//alert(orignName + "-" + targetName);
	if(myName == orignName || myName == targetName)
	{
		var time = getTime();
		var temp = orignName + "(" + time + "):" + content;
		var temp = $("#content").text() + "\n" + temp;
		if(connectingName == orignName || connectingName == targetName)
		{
			$("#content").text(temp);
		}
		else
		{
			$("#noticeName").text(orignName);
			$("#noticeContent").text("发来信息");
			$("#noticeBlock").css({
				"background-color":"white"
			});
		}
		$.post(
			"ConnectionMessageServlet",
			{
				usage:"set",
				orignName:orignName,
				targetName:targetName,
				content:content,
				time:time
			},
			function(result)
			{
				
			}
		);
	}
}

//获取系统时间
function getTime()
{
	var date = new Date();
	var hour = date.getHours() < 10? ("0" + date.getHours()) : date.getHours();
	var minute = date.getMinutes() < 10? ("0" + date.getMinutes()) : date.getMinutes();
	var second = date.getSeconds() < 10? ("0" + date.getSeconds()) : date.getSeconds() ;
	var time = hour + ":" + minute + ":" + second; 
	return time;
}

/*
 *accounts(paramater):
 *name1-name2...
 */
//把在线用户名字显示到网页
function processOnlineAccount(accounts)
{
	$(".btnAccount").remove();
	accounts = accounts.split("-");
	var len = accounts.length;
	for(var i = 0;i < len;i++)
	{
		if(accounts[i] == $("#myName").text())
			continue;
		var temp = "<input type=\"button\" class=\"btnAccount\" value=\"" + accounts[i]  + "\" id=\"" + accounts[i]  + "\"" + " onclick=\"btnAccountClick(this)\"" + " />";
		//alert(temp);
		$("#onlineAccount").append(temp);
	}
}

//设置btnSend
function setBtnSend()
{
	$(document).ready(function(){
		$("#btnSend").click(function(){
			if($("#connectiongName").text() == "" || $("#myName").text() =="" || $("#txtContent").val() == "")
			{
				return ;
			}
			var temp = "orignName>" + $("#myName").text() + "!targetName>" + $("#connectiongName").text() + "!content>" + $("#txtContent").val();
			//alert(temp);
			sendMessage(temp);
			$("#txtContent").val("");
		});
	});
}

//向服务器websocket发送数据
function sendMessage(message)
{
	webSocket.send(encodeURI(message));
}

//============================================================
//在线用户账号按键处理
function btnAccountClick(e)
{
	onBtnAccountClick(e.id);
}

function onBtnAccountClick(id)
{
	if($("#noticeName").text() == id)
	{
		$("#noticeName").text("");
		$("#noticeContent").text("");
		$("#noticeBlock").css({
			"background-color":"#babdb6"
		});
	}
	$("#connectiongName").text(id);
	$.post(
		"ConnectionMessageServlet",
		{
			usage:"get",
			connectiongName:id
		},
		function(result)
		{
			result = decodeURI(result);
			result = result.split(">");
			var temp = "";
			for(var i = 0;i < result.length;i++)
			{
				temp += result[i];
				if(i != result.length - 1)
					temp += ":";
			}
			$("#content").text(temp);
		}
	);
}
//============================================================

//设置noticeBlock
function setNoticeBlockClick()
{
	$(document).ready(function(){
		$("#noticeBlock").click(function(){
			onBtnAccountClick($("#noticeName").text())
		});
	});
}