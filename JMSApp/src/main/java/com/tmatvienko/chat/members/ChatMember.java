package com.tmatvienko.chat.members;

import com.tmatvienko.chat.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Created by tatyana on 11/9/14.
 */
public abstract class ChatMember {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMember.class);

    protected Connection connection;
    protected Session session;
    protected MessageProducer producer;
    protected Destination chat;

    public ChatMember(String destination) throws JMSException, NamingException {
        InitialContext jndi = getInitialContext();
        ConnectionFactory conFactory = (ConnectionFactory) jndi.lookup(Constants.CONNECTION_FACTORY);
        connection = conFactory.createConnection(System.getProperty(Constants.USERNAME), System.getProperty(Constants.PASSWORD));
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        chat = (Destination) jndi.lookup(destination);
    }

    public static InitialContext getInitialContext() throws NamingException {
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, Constants.INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL));
        return new InitialContext(env);
    }

    public void startConnection() throws JMSException {
        connection.start();
    }

    public void closeConnection() {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            LOGGER.error("Exception has been caught during closing the connection", e);
        }
    }
}
