/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.sd.rmi.publishsubscribe.consumer;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rmi.publishsubscribe.producer.EmitLog;

/**
 * Deliver message to multiple consumers (pattern known as "publish/subscribe").
 *
 * Build a simple logging system with two programs: - the first emits log
 * messages and the second receives and prints them. - every running copy of the
 * receiver will get the messages (log messages are going to be broadcasted to
 * all receivers). One receiver directs logs to disk, while another prints logs
 * on the screen.
 *
 * Exchanges: A producer never sends any messages directly to a queue, instead
 * can only send messages to an *exchange*. The exchange receives messages from
 * producers and pushes them to queues, according the exchange type (cf. direct,
 * topic, headers and fanout).
 *
 * Listing exchanges: To list the exchanges on the server you can run
 * rabbitmqctl: sudo rabbitmqctl list_exchanges There will be amq.* exchanges
 * and the default (unnamed or nameless) exchange
 *
 * Temporary queues: Previously we used queues with name (e.g. hello and
 * task_queue) to point producers and workers to the same queue. Now we want to
 * hear about all log messages and the current flowing messages not in the old
 * ones. To solve that we need two things: i) create an empty queue when
 * connecting to rabbitmq ii) once disconnect consumer, the queue should be
 * automatically deleted
 *
 * Binding: tell the exchange to send messages to a queue (i.e. queue is
 * interested in messages from the exchange).
 *
 * Listing bindings: rabbitmqctl list_bindings
 *
 * Running Receivers: java -cp $CP ReceiveLogs > logs.log java -cp $CP
 * ReceiveLogs
 *
 * @author rui
 */
public class ReceiveLogs {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /* Use the Exchange FANOUT type: broadcasts all messages to all queues */
        channel.exchangeDeclare(EmitLog.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        /* Create a non-durable, exclusive, autodelete queue with a generated name.
    The string queueName will contains a random queue name 
    (e.g. amq.gen-JzTY20BRgKO-HjmUJj0wLg) */
        String queueName = channel.queueDeclare().getQueue();

        /* Create binding: tell the exchange to send messages to a queue; the fanout
    exchange ignores the last parameter (routing/binding key) */
        String routingKey = "";
        channel.queueBind(queueName, EmitLog.EXCHANGE_NAME, routingKey);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        /*
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received: '" + message + "'");
      }
    };
    channel.basicConsume(queueName, true, consumer);
         */
        //Use a DeliverCallback instead of a DefaultConsumer for the Receiver
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
