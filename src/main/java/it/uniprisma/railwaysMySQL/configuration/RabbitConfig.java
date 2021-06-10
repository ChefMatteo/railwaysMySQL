package it.uniprisma.railwaysMySQL.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue createTicket(){
        return new Queue("create_ticket");
    }

    @Bean
    public Queue deleteTicket(){
        return new Queue("delete_ticket");
    }

    @Bean
    DirectExchange exchange(){
        return new DirectExchange("railways_exchange");
    }

}
