package com.fun.mouse.server;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fun.mouse.core.MessageHandler;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.alibaba.fastjson.JSONObject;


public class MouseControllWebSocketServer extends WebSocketServer {
    
    public static Map<String, List<WebSocket>> socketMap = new HashMap<>();//频道-进入频道用户的连接列表
    
    public MouseControllWebSocketServer(InetSocketAddress address) {
        super(address);
    }
    
    /**
     * 
     * @param address
     */
    public MouseControllWebSocketServer(InetSocketAddress address, int decoders) {
        super(address, decoders);
    }


    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("onOpen");
        String desc = handshake.getResourceDescriptor().substring(1);//例如页面请求：'ws://127.0.0.1:4444/'+topSid; desc获取到的就是topSid。此处以desc区分不同分类的连接，用于后续通讯
        if( null == socketMap.get(desc)){
            List<WebSocket> list = new ArrayList<>();
            list.add(conn);//connect成功时候执行
            socketMap.put(desc, list);
        } else {
            socketMap.get(desc).add(conn);
        }
    }


    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("onClose");
        Set<String> setList = socketMap.keySet();
        for (String string : setList) {
            if (socketMap.get(string).contains(conn)) {
                socketMap.get(string).remove(conn);//删除连接
                if (socketMap.get(string).isEmpty()) {//连接为空时清除
                    socketMap.remove(string);
                }
                break;
            }
        }
    }


    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("onMessage");
        System.out.println(message);
        /*List<WebSocket> socketList = socketMap.get(message);
        for (WebSocket webSocket : socketList) {
            webSocket.send("已收到~");
        }*/
        try {
        	JSONObject msgObj = JSONObject.parseObject(message);
        	String mtype=msgObj.getString("mtype");
        	if("msg".endsWith(mtype)){
        		System.out.println(msgObj.getString("value"));
        		sendInAllChannel("~已收到~");//测试收到信息时全局发送消息
        		return ;
        	}
        	
        	if("cmd".equals(mtype)){
        		String v = msgObj.getString("value");
        		switch(v){
        			case "UP":
        				MessageHandler.doWheel(msgObj);
        				break;
        			case "DOWN":
        				MessageHandler.doWheel(msgObj);
        				break;
        			case "CLICK":
        				MessageHandler.doClick(msgObj);
        				break;
        			case "MOVE":
        				MessageHandler.doMove(msgObj);
        				break;
        			case "INPUT":
        				MessageHandler.doInput(msgObj);
        			case "SAVE":
        				MessageHandler.doSave();
        				break;
        		}
        	}
        	
		} catch (Exception e) {
			System.out.println("receive message error....");
		}
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("onError");
        Set<String> setList = socketMap.keySet();
        for (String string : setList) {
            if (socketMap.get(string).contains(conn)) {
                socketMap.get(string).remove(conn);//删除连接
                if (socketMap.get(string).isEmpty()) {//连接为空时清除
                    socketMap.remove(string);
                }
                break;
            }
        }
    }
    

    /**
     * 发送消息到某一个连接
     * @param channel
     * @param message
     */
    public void sendInOneChannel(String channel,String message){
        List<WebSocket> list = socketMap.get(channel);
        for (WebSocket webSocket : list) {
            webSocket.send(message);
        }
    }
    
    /**
     * 发送消息到所有连接
     * @param message
     */
    public void sendInAllChannel(String message){
        Set<String> setList = socketMap.keySet();
        for (String string : setList) {
            List<WebSocket> list = socketMap.get(string);
            for (WebSocket webSocket : list) {
                webSocket.send(message);
            }
        }
    }

}