package com.fun;

import com.fun.http.HttpServer;
import com.fun.mouse.server.MouseControllWebSocketServer;

import java.net.InetSocketAddress;

/**
 * Created by ifun on 2016/10/31.
 *
 */
public class Mouse {
    public static void main(String[] args) throws InterruptedException {
        //startMouseServer();
        startHttpServer();
    }

    public static void startMouseServer(){
        // 启动鼠标控制websocket服务器
        InetSocketAddress address = new InetSocketAddress(4444);
        MouseControllWebSocketServer socket = new MouseControllWebSocketServer(address);
        socket.start();

        System.out.println("-----mouse server started------");


    }

    public static void startHttpServer(){


        // 启动http服务
        Thread server = new Thread(new HttpServer());
        server.start();

        System.out.println("-----http server started------");
    }


}
