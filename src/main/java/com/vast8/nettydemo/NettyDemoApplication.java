package com.vast8.nettydemo;

import com.vast8.nettydemo.server.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetSocketAddress;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/29 13:57
 */
@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {

    @Value("${netty.port:7000}")
    private int port;

    @Value("${netty.url:127.0.0.1}")
    private String url;

    @Autowired
    private NettyServer socketServer;


    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress( port);
        ChannelFuture future = socketServer.run(address);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                socketServer.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
