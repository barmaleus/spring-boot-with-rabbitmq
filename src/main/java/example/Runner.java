package example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Listener listener;

    public Runner(Listener listener, RabbitTemplate rabbitTemplate) {
        this.listener = listener;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(Application.emergencyFanoutExchangeName, "hello.all.info", "Hello from RabbitMQ!");
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(
                    Application.workFanoutExchangeName,
                    "worker.any.info",
                    "Common work number: " + i);
            rabbitTemplate.convertAndSend(
                    Application.emergencyFanoutExchangeName,
                    "worker.all.warn",
                    "Please, follow the safety instructions!" + i);
            rabbitTemplate.convertAndSend(
                    Application.frontendWorkTopicExchangeName,
                    "worker.frontend.info",
                    "Frontend work number: " + i);
            rabbitTemplate.convertAndSend(
                    Application.backendWorkTopicExchangeName,
                    "worker.backend.info",
                    "Backend work number: " + i);
            Thread.sleep(1000);
        }

        listener.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}
