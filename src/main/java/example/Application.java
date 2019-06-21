package example;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class Application {

    static final String emergencyFanoutExchangeName = "emergency.fanout.exchange";
    static final String workFanoutExchangeName = "work.fanout.exchange";
    static final String frontendWorkTopicExchangeName = "frontend.work.topic.exchange";
    static final String backendWorkTopicExchangeName = "backend.work.topic.exchange";

    static final String commonQueueName = "common-work-queue";
    static final String worker1QueueName = "worker-1-queue";
    static final String worker2QueueName = "worker-2-queue";

    @Primary
    @Bean
    Queue commonQueue() {
        return new Queue(commonQueueName, false);
    }

    @Bean
    Queue worker1Queue() {
        return new Queue(worker1QueueName, false);
    }

    @Bean
    Queue worker2Queue() {
        return new Queue(worker2QueueName, false);
    }

    @Bean
    FanoutExchange workFanoutExchange() {
        return new FanoutExchange(workFanoutExchangeName);
    }

    @Bean
    FanoutExchange emergencyFanoutExchange() {
        return new FanoutExchange(emergencyFanoutExchangeName);
    }

    @Bean
    TopicExchange frontendWorkTopicExchange() {
        return new TopicExchange(frontendWorkTopicExchangeName);
    }

    @Bean
    DirectExchange backendWorkTopicExchange() {
        return new DirectExchange(backendWorkTopicExchangeName);
    }

    @Bean
    Binding workQueueBinding() {
        return BindingBuilder.bind(commonQueue()).to(workFanoutExchange());
    }

    @Bean
    Binding emergencyBinding1() {
        return BindingBuilder.bind(worker1Queue()).to(emergencyFanoutExchange());
    }

    @Bean
    Binding emergencyBinding2() {
        return BindingBuilder.bind(worker2Queue()).to(emergencyFanoutExchange());
    }

    @Bean
    Binding fronendBinding() {
        return BindingBuilder.bind(worker1Queue()).to(frontendWorkTopicExchange()).with("*.frontend.#");
    }

    @Bean
    Binding backendBinding() {
        return BindingBuilder.bind(worker2Queue()).to(backendWorkTopicExchange()).with("worker.backend.info");
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args).close();
    }

}
