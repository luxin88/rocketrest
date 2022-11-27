package com.rocket.rest.rocketrest.controller;

import com.rocket.rest.rocketrest.model.TestObj;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.apache.rocketmq.client.apis.*;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.MessageListener;
import org.apache.rocketmq.client.apis.consumer.SimpleConsumer;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.message.MessageBuilder;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import java.time.Duration;
import java.util.List;

@RestController
public class rest {
    @RequestMapping(path = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public static String test() {
        return "hello";
    }

    @RequestMapping(path = "/arr", method = {RequestMethod.GET, RequestMethod.POST})
    public static int[] asArray() {
        int[] result = {1,2,3};
        return result;
    }

    @RequestMapping(path = "/obj", method = {RequestMethod.GET, RequestMethod.POST})
    public static TestObj asObj() {
        return new TestObj("张三", 35, "11011011010");
    }

    @RequestMapping(path = "/queue", method = {RequestMethod.POST})
    public static String putQueue() {
        String endpoint = "localhost:8081";
        String topic = "Your Topic";

        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoint);
        ClientConfiguration configuration = builder.build();
        Producer producer = provider.newProducerBuilder()
                .setTopics(topic)
                .setClientConfiguration(configuration)
                .build();
        Message message = provider.newMessageBuilder()
                .setTopic(topic)
                .setKeys("messageKey")
                .setTag("messageTag")
                .setBody("messageBody".getBytes())
                .build();
        try {
            SendReceipt sendReceipt = producer.send(message);
            System.out.println(sendReceipt.getMessageId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "";
    }
}
