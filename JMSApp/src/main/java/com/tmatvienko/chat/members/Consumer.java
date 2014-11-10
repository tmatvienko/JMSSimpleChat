package com.tmatvienko.chat.members;

import javax.jms.*;
import javax.naming.NamingException;

/**
 * Created by tatyana on 11/9/14.
 */
public class Consumer extends ChatMember {

    private MessageConsumer messageConsumer;

    public Consumer(String destination) throws NamingException, JMSException {
        super(destination);
        this.messageConsumer = session.createConsumer(chat);
    }

    public void runMessageConsumer(MessageListener messageListener) throws JMSException {
        messageConsumer.setMessageListener(messageListener);
        startConnection();
    }
}
