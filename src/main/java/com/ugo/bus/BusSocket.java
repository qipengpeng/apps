package com.ugo.bus;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;



@ServerEndpoint("/bus/{user}/{deviceId}")
public class BusSocket {
	
	private Session mSession;
	
	public BusSocket() {
		System.out.println("Created bus socket...");
	}
	
	private static ConcurrentHashMap<String, BusSocket> connections = new ConcurrentHashMap<String, BusSocket>(); 
	
	public static void sendMessage(String deviceId,String msg) {
		for(String key : connections.keySet()) {
			if(key.endsWith("-"+deviceId)) {
				if(connections.get(key).isOpen()) {
					connections.get(key).sendMessage(msg);
				}
			}
		}
	}
	
	public boolean isOpen() {
		return mSession.isOpen();
	}
	
	public void sendMessage(String msg) {
		  try {
			mSession.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

	@OnOpen
	public void onOpen(Session session) {
		this.mSession = session;
		String user = session.getPathParameters().get("user");
		String deviceId = session.getPathParameters().get("deviceId");
		connections.put(user+"-"+deviceId, this);
	
		System.out.println("Open Socket: user=" + user + " deviceId="+deviceId+" size:"+connections.size());
		
	}

	/**
	 * 收到客户端消息时触发
	 * 
	 * @param relationId
	 * @param userCode
	 * @param message
	 * @return
	 */
	@OnMessage
	public String onMessage(Session session, String message) {
		System.out.println("pathParams:" + session.getPathParameters());
		System.out.println("requestParams" + session.getRequestParameterMap());
		return "OnMessage " + message;
	}

	/**
	 * 异常时触发
	 * 
	 * @param relationId
	 * @param userCode
	 * @param session
	 */
	@OnError
	public void onError(Throwable throwable, Session session) {
		System.out.println("pathParams:" + session.getPathParameters());
		System.out.println("requestParams" + session.getRequestParameterMap());
		System.out.print("onError" + throwable.toString());
//		String user = session.getPathParameters().get("user");
//		String deviceId = session.getPathParameters().get("deviceId");
		//connections.remove(user+"-"+deviceId);
	}

	/**
	 * 关闭连接时触发
	 * 
	 * @param relationId
	 * @param userCode
	 * @param session
	 */
	@OnClose
	public void onClose(Session session) {
		String user = session.getPathParameters().get("user");
		String deviceId = session.getPathParameters().get("deviceId");
		connections.remove(user+"-"+deviceId);
		System.out.println("Close Socket: user=" + user + " deviceId="+deviceId+" size:"+connections.size());
	}
}
