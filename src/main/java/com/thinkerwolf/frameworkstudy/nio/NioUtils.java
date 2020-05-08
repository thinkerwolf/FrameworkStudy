package com.thinkerwolf.frameworkstudy.nio;

import java.io.IOException;
import java.nio.channels.Channel;

public class NioUtils {

    public static void closeChannel(Channel ch) {
        try {
            ch.close();
        } catch (IOException ignored) {
        }
    }


}
