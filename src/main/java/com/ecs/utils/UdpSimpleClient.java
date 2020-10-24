package com.ecs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

@Component
public class UdpSimpleClient {

    private final static Logger logger = LoggerFactory.getLogger(UdpSimpleClient.class);

    @Value("${udp.port}")
    private Integer udpPort;

    public void sendMessage(String message) {
        logger.info("发送UDP: {}", message);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("255.255.255.255", udpPort);
        byte[] udpMessage = message.getBytes();
        DatagramPacket datagramPacket = null;
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramPacket = new DatagramPacket(udpMessage, udpMessage.length, inetSocketAddress);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("发送成功");
    }
}
