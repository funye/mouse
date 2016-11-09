package com.fun.http;

import com.fun.utils.Config;

import java.io.*;
import java.util.Date;

/**
 * Created by yehuan on 2016/10/31.
 *
 */
public class Response {

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        InputStream in = null;
        try {
            //将web文件写入到OutputStream字节流中
            in = this.getClass().getResourceAsStream(HttpServer.WEB_ROOT+request.getUri());
            if (in != null) {

                /**
                 *  HTTP/1.1 200 ok
                 X-Powered-By: Express
                 Accept-Ranges: bytes
                 Cache-Control: public, max-age=0
                 Last-Modified: Mon, 31 Oct 2016 13:52:49 GMT
                 ETag: W/"180c-1581b039609"
                 Date: Mon, 31 Oct 2016 15:30:38 GMT
                 Connection: keep-alive
                 */

                output.write("HTTP/1.1 200 ok\r\n".getBytes());
                output.write("Accept-Ranges:bytes\r\n".getBytes());
                output.write("Cache-Control:no-cache\r\n".getBytes());
                output.write(("Content-Length: "+in.available()+"\r\n").getBytes());
                output.write("Content-Type: text/html; charset=UTF-8\r\n".getBytes());
                output.write(("Date:"+new Date()+"\r\n").getBytes());
                output.write(("X-Powered-By: fun server\r\n").getBytes());
//                output.write(("ETag: W/\"180c-1581b039609\"\r\n").getBytes());
                output.write(("Last-Modified:"+new Date()+"\r\n").getBytes());
                output.write("Connection:keep-alive\r\n\r\n".getBytes());

                output.flush();

                int ch = in.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = in.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                // file not found
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n";
                output.write(errorMessage.getBytes());
            }
        } catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString());
        } finally {
            if (in != null)
                in.close();
        }
    }
}
