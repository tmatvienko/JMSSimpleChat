package com.tmatvienko.chat;

import com.tmatvienko.chat.constants.Constants;
import com.tmatvienko.chat.members.Consumer;
import com.tmatvienko.chat.members.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by tatyana on 11/9/14.
 */
public class JMSChat implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JMSChat.class);

    private Producer producer;
    private Consumer consumer;

    public JMSChat(String destination) throws Exception {

        try {
            producer = new Producer(destination);
            consumer = new Consumer(destination);

            producer.runMessageProducer();
            consumer.runMessageConsumer(this);
        } catch (Exception e) {
            LOGGER.error("Exception has been caught during JMSChat creation", e);
            throw e;
        }
    }


    @Override
    public void onMessage(Message message) {
        try {
            System.out.println( ((TextMessage) message).getText() );
        } catch (JMSException jmse) {
            LOGGER.error("Exception has been caught during message processing: " + message, jmse);

        }
    }

    public static void main(String[] args) {
        LOGGER.info("JMSChat creation process has been started with arguments: {}", args);
        if (args.length != 1) {
            LOGGER.error("Username is missing");
            System.exit(0);
        }

        JMSChat chat = null;
        try {
            chat = new JMSChat(Constants.DESTINATION_NAME_TOPIC);

            BufferedReader commandLine = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                try {
                    String msg = commandLine.readLine();
                    if ("exit".equalsIgnoreCase(msg)) {
                        chat.producer.closeConnection();
                        chat.consumer.closeConnection();
                        System.exit(0);
                    } else {
                        chat.producer.writeMessage(String.format("[%s]: %s", args[0], msg));
                    }
                } catch (IOException e) {
                    LOGGER.error("Exception has been caught during reading input stream", e);
                    throw e;
                } catch (JMSException e) {
                    LOGGER.error("Exception has been caught during JMS work", e);
                    throw e;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Exception has been caught", ex);
        } finally {
            if (chat != null) {
                if (chat.producer != null) {
                    chat.producer.closeConnection();
                }
                if (chat.consumer != null) {
                    chat.consumer.closeConnection();
                }
            }
        }
    }
}
