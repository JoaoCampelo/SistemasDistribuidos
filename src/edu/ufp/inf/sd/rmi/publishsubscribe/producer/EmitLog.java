package edu.ufp.inf.sd.rmi.publishsubscribe.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLog {

    public static final String EXCHANGE_NAME = "logs_exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        //Try-with-resources
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            /* Set the Exchange type to FANOUT (multicast to all queues). */
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String message = getMessage(argv);

            /* Publish messages to the logs_exchange instead of the nameless one.
          We could supply a routingKey ("") when sending, but fanout exchanges ignores it.
          Messages will be lost if no queue is bound to the exchange yet. */
            String routingKey = "";
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent: '" + message + "'");

        }
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1) {
            return "info: Hello World!";
        }
        return joinStrings(strings, 3, " ");
    }

    private static String joinStrings(String[] strings, int start, String delimiter) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        StringBuilder words = new StringBuilder(strings[start]);
        System.out.println("EmitLog - joinStrings(): string[" + start + "] = " + strings[start]);
        for (int i = start + 1; i < length; i++) {
            System.out.println("EmitLog - joinStrings(): string[" + i + "] = " + strings[i]);
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
