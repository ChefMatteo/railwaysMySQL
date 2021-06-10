package it.uniprisma.railwaysMySQL.models.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TicketListener {

    @RabbitListener(bindings = @QueueBinding(value = @Queue("delete_ticket"), exchange = @Exchange("railways_exchange"), key = "delete"))
    public void deleteListener(String message){
        log.info("Received <{}> for delete", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue("create_ticket"), exchange = @Exchange("railways_exchange"), key = "create"))
    public void createListener(String message){
        log.info("Received <{}> for create", message);
    }


}
