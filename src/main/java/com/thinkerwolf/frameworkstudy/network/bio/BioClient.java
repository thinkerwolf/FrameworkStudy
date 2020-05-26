package com.thinkerwolf.frameworkstudy.network.bio;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClient {

    private SocketFactory socketFactory = SocketFactory.getDefault();

    private Socket socket;

    public BioClient() throws IOException {
        this.socket = socketFactory.createSocket();
    }

    public void connect(String host, int port) throws IOException {
        socket.connect(new InetSocketAddress(host, port));


        //TODO 发送 接受
    }


}
