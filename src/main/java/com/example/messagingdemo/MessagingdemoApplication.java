package com.example.messagingdemo;

import com.azure.spring.messaging.implementation.annotation.EnableAzureMessaging;
import com.example.messagingdemo.serviceBus.ServiceBusProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAzureMessaging
@ImportAutoConfiguration(com.azure.spring.cloud.autoconfigure.implementation.messaging.AzureMessagingListenerAutoConfiguration.class)
public class MessagingdemoApplication {

    @Autowired
    ServiceBusProducer serviceBusProducer;
	public static void main(String[] args) {
		SpringApplication.run(MessagingdemoApplication.class, args);
	}

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
                  
			MenuOrder menuOrder = new MenuOrder();
			menuOrder.setCustomerName("Customer1");	
			menuOrder.setOrderList(Arrays.asList("Pizza", "Pasta", "Burger"));				
            try {
                serviceBusProducer.SendMsgQ1Loop(menuOrder);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        };

	}
        

}
