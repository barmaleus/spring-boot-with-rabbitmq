## Simple Spring Boot project with RabbitMQ which used for messaging.

There are ***2 Listeners*** (workers: frontend and backend developer).

They have queues of messages: ***one common queue*** and ***2 private queues***.

Messages are generated in Runner class and are transmitted to exchanges.

There are ***4 types of messages*** (common work messages, frontend or backend work messages and emergency messages).

Emergency messages are recevied by all workers,  
common messages - by all workers, but only in course,  
frontend messages are received only by first worker,  
backend - receives only second worker.
    
To realize it we use different bindings.  
*May tell more about bindings*
***
The output should be something like this:

        Sending message...
        Worker 2 received message <Hello from RabbitMQ!>
        Worker 1 received message <Hello from RabbitMQ!>
        Worker 2 received message <Please, follow the safety instructions!0>
        Worker 1 received message <Please, follow the safety instructions!0>
        Worker 2 received message <Common work number: 0>
        Worker 1 received message <Frontend work number: 0>
        Worker 2 received message <Backend work number: 0>
        Worker 1 received message <Common work number: 1>
        Worker 2 received message <Please, follow the safety instructions!1>
        Worker 1 received message <Please, follow the safety instructions!1>
        Worker 1 received message <Frontend work number: 1>
        Worker 2 received message <Backend work number: 1>
        Worker 2 received message <Common work number: 2>
        Worker 1 received message <Please, follow the safety instructions!2>
        Worker 1 received message <Frontend work number: 2>
        Worker 2 received message <Please, follow the safety instructions!2>
        Worker 2 received message <Backend work number: 2>
        Worker 2 received message <Please, follow the safety instructions!3>
        Worker 1 received message <Common work number: 3>
        Worker 2 received message <Backend work number: 3>
        Worker 1 received message <Please, follow the safety instructions!3>
        Worker 1 received message <Frontend work number: 3>
        etc ...
        
RabbitMQ runs on Docker