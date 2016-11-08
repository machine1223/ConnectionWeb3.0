package websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

@ServerEndpoint("/ConnectionWebSocket")
public class ConnectionWebSocket{
	
	private static ArrayList<Session> sessions = new ArrayList<Session>();
	
	@SuppressWarnings("static-access")
	@OnMessage
	public void onMessage(String message,Session session) throws IOException
	{
		for(Session temp:this.sessions)
		{
			temp.getBasicRemote().sendText(message);
		}
	}
	
	@SuppressWarnings("static-access")
	@OnOpen
	public void onOpen(Session session) {
		// TODO Auto-generated method stub
		this.sessions.add(session);
	}
	
	@SuppressWarnings("static-access")
	@OnClose
    public void onClose(Session session) {  
        this.sessions.remove(session);
    }
/*
	@OnError
	public void onError(Session session)
	{
		
	}
	
*/
	
	
}
