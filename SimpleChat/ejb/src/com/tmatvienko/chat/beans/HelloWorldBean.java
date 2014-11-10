package com.tmatvienko.chat.beans;

import javax.ejb.Stateless;

/**
 * Created by tatyana on 11/10/14.
 */
@Stateless(name = "HelloWorldBeanEJB")
public class HelloWorldBean {
    public HelloWorldBean() {
    }

    public String sayHello() {
        return "Hello, World!";
    }
}
