package cn.putao.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yehuan on 2016/10/31.
 *
 */
public class HttpServer {
    /**
     * WEB_ROOT是HTML和其它文件存放的目录. 这里的WEB_ROOT为工作目录下的webroot目录
     */
    //public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static final String WEB_ROOT = HttpServer.class.getResource("/").getPath()+"webapp";

    // 关闭服务命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private static final int PORT = 8080;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
//        System.out.println(WEB_ROOT);

    }

    public void await() {
        ServerSocket serverSocket = null;
        try {
            //服务器套接字对象
            serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName(HOST));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 循环等待一个请求
        while (true) {
            Socket socket ;
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
                // 检查是否是关闭服务命令
                if (request.getUri().equals(SHUTDOWN_COMMAND)) {
                    break;
                }

                // 创建 Response 对象
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // 关闭 socket 对象
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
