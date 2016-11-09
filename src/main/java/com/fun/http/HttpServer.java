package com.fun.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.BreakIterator;

/**
 * Created by yehuan on 2016/10/31.
 *
 */
public class HttpServer implements Runnable{
    /**
     * WEB_ROOT是HTML和其它文件存放的目录. 这里的WEB_ROOT为工作目录下的webroot目录
     */
    //public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static final String WEB_ROOT = HttpServer.class.getResource("/").getPath()+"webapp";

    // 关闭服务命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    ServerSocket serverSocket = null;
    Socket socket = null;

    private int PORT = 8080;
    private String HOST = "127.0.0.1";

    private boolean isStop = false;

    public HttpServer(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
    }

    public HttpServer(String HOST) {
        this.HOST = HOST;
    }

    public HttpServer() {

    }


    @Override
    public void run() {

        try {
            //服务器套接字对象
            serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName(HOST));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 循环等待一个请求
        while (!isStop) {

            InputStream input ;
            OutputStream output ;
            try {
                //等待连接，连接成功后，返回一个Socket对象
                socket = serverSocket.accept();

                input = socket.getInputStream();
                output = socket.getOutputStream();

                // 创建Request对象并解析
                Request request = new Request(input);
                request.parse();

                // 创建 Response 对象
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // 关闭 socket 对象
                socket.close();

            } catch (SocketException e) {
//                e.printStackTrace();
                shutdown();
                System.out.println("socket colsed....");
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }// end od while

    }// end of run

    public void shutdown() {
        if(serverSocket != null){
            try {
                serverSocket.close();
                isStop = true;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("stop the server 1....");
            }
        }
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("stop the server 2....");
            }
        }
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

}
