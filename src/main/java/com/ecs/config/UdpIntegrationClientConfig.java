package com.ecs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;

/**
 * IntegrationClientConfig
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/20 15:59
 */
@Configuration
public class UdpIntegrationClientConfig {

    @Value("${udp.port}")
    private Integer udpPort;

    @Bean
    @ServiceActivator(inputChannel = "udpOut")
    public UnicastSendingMessageHandler unicastSendingMessageHandler() {
        UnicastSendingMessageHandler unicastSendingMessageHandler = new UnicastSendingMessageHandler("255.255.255.255", udpPort);
        return unicastSendingMessageHandler;
    }

}
