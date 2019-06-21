package example;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class Listener {

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = {Application.commonQueueName, Application.worker1QueueName})
    public void worker1ReceiveMessage(String message) {
        System.out.println("Worker 1 received message <" + message + ">");
        latch.countDown();
    }

    @RabbitListener(queues = {Application.commonQueueName, Application.worker2QueueName})
    public void worker2ReceiveMessage(String message) {
        System.out.println("Worker 2 received message <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
