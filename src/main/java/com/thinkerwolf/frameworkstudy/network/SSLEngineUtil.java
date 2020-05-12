package com.thinkerwolf.frameworkstudy.network;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * TCP SSLEngine 客户端
 *
 * @author wukai
 * @date 2020/5/12 11:55
 */
public class SSLEngineUtil {


    /**
     * @param host
     * @param port
     * @return
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    public static SSLEngine createSSLEngine(String host, int port) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        char[] passpharse = "123456".toCharArray();
        KeyStore ksKeys = KeyStore.getInstance("JKS");
        ksKeys.load(new FileInputStream("C:\\Users\\wukai\\.keystore"), passpharse);
        KeyStore ksTrust = KeyStore.getInstance("JKS");
        ksTrust.load(new FileInputStream("C:\\Users\\wukai\\.keystore"), passpharse);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ksKeys, passpharse);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ksTrust);

        SSLContext.getDefault().createSSLEngine();
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        SSLEngine sslEngine = sslContext.createSSLEngine(host, port);

        sslEngine.setUseClientMode(true);
        return sslEngine;
    }


    public static void main(String[] args) throws Exception {
        SSLEngine engine = createSSLEngine("127.0.0.1", 8080);

    }

}
