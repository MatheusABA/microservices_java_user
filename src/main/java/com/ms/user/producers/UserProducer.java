package com.ms.user.producers;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {


    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;


    public void publishMessage(User user) {
        var emailDto = new EmailDto();
        emailDto.setUserId(user.getUserId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Successful registration");
        emailDto.setBody(user.getName() + ", welcome to our application! \nWe appreciate your registration to our website, fell free to use our platform as you wish !!");

        // parameters(exchange: Who will receive the message on broker, routingKey: url to the queue, object: the message)
        rabbitTemplate.convertAndSend("", routingKey, emailDto);

    }

}
