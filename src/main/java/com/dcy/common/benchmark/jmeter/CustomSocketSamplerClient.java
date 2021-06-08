package com.dcy.common.benchmark.jmeter;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * write description here
 *
 * @author dcy
 * @version 2019-05-06 13:41 Create Date: 2019-05-06 13:41
 */
public abstract class CustomSocketSamplerClient extends AbstractJavaSamplerClient {
    private String hostname;
    private int port;
    private Socket socket;
    public InputStream inputStream;
    public OutputStream outputStream;
    public byte[] transferData;

    public CustomSocketSamplerClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.socket = new Socket();

    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        try {
            socket.connect(new InetSocketAddress(hostname, port));
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (IOException e) {
           getNewLogger().error("",e);
        }
    }


    @Override
    public void teardownTest(JavaSamplerContext context) {
        try {
            socket.close();
        } catch (IOException e) {
            getNewLogger().error("",e);

        }
    }
}
