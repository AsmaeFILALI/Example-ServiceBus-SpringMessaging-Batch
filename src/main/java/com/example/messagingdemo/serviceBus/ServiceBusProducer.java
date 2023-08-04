package com.example.messagingdemo.serviceBus;

import com.azure.spring.messaging.servicebus.core.ServiceBusTemplate;
import com.example.messagingdemo.MenuOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ServiceBusProducer {

    @Value("${app.msgNum}")
    int messageNum;

    @Autowired
    private ServiceBusTemplate serviceBusTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final String QUEUE1_NAME = "demoqueue";
    private static final String QUEUE2_NAME = "queueaks";



    public void SendMsgQ1Loop(MenuOrder menuOrder) throws JsonProcessingException {
        
        
        for(int i = 0;i<messageNum;i++)
       { 
        MenuOrder menuOrder1 = new MenuOrder();
        menuOrder1.setOrderId(i);
        menuOrder1.setOrderIdentifier(String.valueOf(i));
        menuOrder1.setCustomerName(menuOrder.getCustomerName());
        menuOrder1.setOrderList(menuOrder.getOrderList());

        SendMsgQ1(menuOrder1);
    }
}
    public void SendMsgQ1(MenuOrder menuOrder) throws JsonProcessingException {
        serviceBusTemplate.sendAsync(QUEUE1_NAME, MessageBuilder.withPayload(objectMapper.writeValueAsString(menuOrder)).build()).subscribe();
        log.info("*******************ServiceBUS_MessageSent************: {}", menuOrder.toString());
    }


    public void SendMsgQ2(MenuOrder menuOrder) throws JsonProcessingException {
        serviceBusTemplate.sendAsync(QUEUE2_NAME, MessageBuilder.withPayload(objectMapper.writeValueAsString(menuOrder)).build()).subscribe();
        log.info("*******************ServiceBUS_MessageSent************: {}", menuOrder.toString());
    }


}



