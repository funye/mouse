package com.fun.mouse.core;

import java.awt.event.InputEvent;

import com.alibaba.fastjson.JSONObject;

public class MessageHandler {
	
	public static final int TIMES = 5;
	
	public static void doMove(JSONObject msgObj){
		JSONObject data = msgObj.getJSONObject("data");
		int x = data.getInteger("x");
		int y = data.getInteger("y");
		MouseControll.getInstance().mouseMove(x*TIMES, y*TIMES);
	}
	
	public static void doClick(JSONObject msgObj){
		JSONObject data = msgObj.getJSONObject("data");
		int button = data.getInteger("button");
		System.out.println(button);
		switch(button){
			case 0:
				System.out.println("left click");
				MouseControll.getInstance().btnClick(InputEvent.BUTTON1_MASK);// left
				break;
			case 1:
				System.out.println("right click");
				MouseControll.getInstance().btnClick(InputEvent.BUTTON3_MASK);// right
				break;
		}
	}
	
	public static void doSave(){
		MouseControll.getInstance().savePoint();
	}

	public static void doWheel(JSONObject msgObj){
		
	}
	
	public static void doInput(JSONObject msgObj){
		
	}
}
