package com.thinkerwolf.frameworkstudy.network.bio;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * SSLSocket 客户端
 *
 * @author wukai
 * @date 2020/5/12 11:55
 */
public class SSLSocketClient {

    SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

    private SSLSocket socket;

    public SSLSocketClient() throws IOException {
        this.socket = (SSLSocket) sslSocketFactory.createSocket("127.0.0.1", 7070);
    }

    public static void main(String args[]) throws IOException {
        new SSLSocketClient().connect();
    }

    public void connect() {
        try {
            // 获取客户端套接字输出流
            PrintWriter output = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            // 将用户名和密码通过输出流发送到服务器端
            String userName = "principal";
            output.println(userName);
            String password = "credential";
            output.println(password);
            output.flush();

            // 获取客户端套接字输入流
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            // 从输入流中读取服务器端传送的数据内容，并打印出来
            String response = input.readLine();
            response += "\n " + input.readLine();
            System.out.println(response);

            // 关闭流资源和套接字资源
            output.close();
            input.close();
            socket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
