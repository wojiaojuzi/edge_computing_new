package com.ecs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class UdpIntegrationClient {
    private final static Logger logger = LoggerFactory.getLogger(UdpIntegrationClient.class);

    @Autowired
    private UnicastSendingMessageHandler unicastSendingMessageHandler;

    public void sendMessage(String message) {
        logger.info("发送UDP: {}", message);
        unicastSendingMessageHandler.handleMessage(MessageBuilder.withPayload(message).build());
        logger.info("发送成功");
    }
}
