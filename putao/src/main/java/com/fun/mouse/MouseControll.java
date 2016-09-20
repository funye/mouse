package com.fun.mouse;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;


public class MouseControll {
	
	private Robot robot;
	
	private static MouseControll instance;
	private static Point point;
	
	private MouseControll(){
		try {
			robot = new Robot();
			point = new Point(0,0);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public static MouseControll getInstance(){
		if(instance == null){
			instance = new MouseControll();
		}
		return instance;
	}
	
	public void mouseMove(int xx,int yy) {
		try {
			Robot robot = MouseControll.getInstance().getRobot();
			System.out.println("on move point (x,y)=("+point.x+","+point.y+")");
			robot.mouseMove(point.x+xx,point.y+yy);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public void btnClick(int button){
		Point point = MouseInfo.getPointerInfo().getLocation();
		System.out.println("point (x,y)=("+point.x+","+point.y+")");
		
		Robot robot = MouseControll.getInstance().getRobot();
		robot.mousePress(button);
		robot.mouseRelease(button);
	}
	
	public void savePoint(){
		MouseControll.point = MouseInfo.getPointerInfo().getLocation();
	}

	private Robot getRobot() {
		return robot;
	}
	
}
