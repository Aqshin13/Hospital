package com.hospital.config;


import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue otpMail(){
        return new Queue("otp-mail");
    }

    @Bean
    public Queue activeUser(){
        return new Queue("activate-mail");
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("exchange");
    }

    @Bean
    public Binding bindingForOtp(){
        return BindingBuilder
                .bind(otpMail())
                .to(exchange())
                .with("mailotp-routing-key");
    }

    @Bean
    public Binding bindingForActivating(){
        return BindingBuilder
                .bind(activeUser())
                .to(exchange())
                .with("mailactivate-routing-key");
    }


    @Bean
    public MessageConverter converter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
