<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>웹소켓 채팅</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.min.css">
    
    <style>	
   .chat_wrap{width:800px; padding:5px; font-size:18px;}
   .chat_wrap .inner{background-color:#acc2d2; border-radius:5px; padding:10px; overflow-y:scroll; height:400px}
   
   input[type="text"]{border:0;; width:100%; background:#ddd; border-radius:5px; height:30px; padding-left:5px; box-sizing:border-box; margin-top:5px}
   input[type="text"]::placeholder{color:#999}
		
		#message{
		width:800px;
		}

    </style>
        
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
<script type="text/javascript">

var csrfHeaderName = "${_csrf.headerName}";
var csrfTokenValue = "${_csrf.token}";

   let webSocket = {
		   
		   init: function(param){
			   this._url = param.url;
			   this._initSocket();
		   },
		   sendChat: function(){
			   this._sendMessage('${param.room_id}','CMD_MSG_SEND',$('#message').val(), '${param.userId}');
			   $('#message').val('');

		   },
		   sendEnter: function(){
			   this._sendMessage('${param.room_id}','CMD_ENTER', $('#message').val(), '${param.userId}');
			   $('#message').val('');
		   },
		   receiveMessage: function(msgData){
			   
			   // 정의된 CMD 코드에 따라서 분기 처리
			   if(msgData.cmd == 'CMD_MSG_SEND'){

				   
			   $('#divChatData').append('<div>' + msgData.msg + '</div>');
			   }
			   // 입장
			   else if(msgData.cmd == 'CMD_ENTER'){
				   $('#divChatData').append('<div>' + msgData.msg + '</div>');   
			    }
			   // 퇴장
			   else if(msgData.cmd == 'CMD_EXIT'){
				   $('#divChatData').append('<div>' + msgData.msg + '</div>');
			   }
		    },
		   
		   closeMessage: function(str){
			   $('#divChatData').append('<div>' + '연결 끊김 : ' + str + '</div>');
		   },
		   disconnect: function(){
			   this._socket.close();
		   },
		   _initSocket: function(){
			   this._socket = new SockJS(this._url);
			   this._socket.onopen = function(evt){
				   webSocket.sendEnter();
			   };
			   this._socket.onmessage = function(evt){
				   webSocket.receiveMessage(JSON.parse(evt.data));
			   };
			   this._socket.onclose = function(evt){
				   webSocket.closeMessage(JSON.parse(evt.data));
			   }
		   },
		   _sendMessage: function(room_id, cmd, msg, userId){
			   
			   let msgData = {
					   room_id : room_id,
					   cmd : cmd,
					   msg : msg,
					   userId : userId
			   };

			   let jsonData = JSON.stringify(msgData);
			   this._socket.send(jsonData);
			   $(".chat_wrap .inner").stop().animate({scrollTop:$(".chat_wrap .inner").height()},500);
		   }
   };
   
			 
	 

</script>
<script type="text/javascript">
    $(window).on('load',function(){
    	webSocket.init({ url: '<c:url value="/chat" />' });
    });
</script>

</head>
<body>

<div class="chat_wrap">
    <div class="inner">
    
         
	<div style="width: 500px; height: 200px; padding: 10px;">
	     <div id="divChatData"></div>
	</div>   

  </div><!-- inner -->
</div><!-- chat_wrap -->

    <div class="chat">
	    <input type="text" id="message" size="110" onkeypress="if(event.keyCode==13){webSocket.sendChat();}"/>
	    <input type="button" id="btnSend" value="채팅 전송" onclick="webSocket.sendChat()"/>
	</div>
	
	<span class="main-header">${param.userId }님,환영합니다.</span>
	<span class="main-header">${param.room_id }방으로 환영합니다.</span>


    
</body>
</html>