package com.tmatvienko.chat.members;

import com.tmatvienko.chat.members.ChatMember;

import javax.jms.*;
import javax.naming.NamingException;

/**
 * Created by tatyana on 11/9/14.
 */
public class Producer extends ChatMember {

    private MessageProducer messageProducer;

    public Producer(String destination) throws NamingException, JMSException {
        super(destination);
        this.messageProducer = session.createProducer(chat);
    }

    public void runMessageProducer() throws JMSException {
        startConnection();
    }

    public void writeMessage(String text) throws JMSException {
        TextMessage message = session.createTextMessage(text);
        messageProducer.send(message);
    }
}
