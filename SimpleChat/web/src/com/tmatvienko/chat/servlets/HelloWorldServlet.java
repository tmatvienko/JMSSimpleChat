package com.tmatvienko.chat.servlets;

import com.tmatvienko.chat.beans.HelloWorldBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tmatvienko on 11/10/14.
 */
@WebServlet(name = "HelloWorldServlet")
public class HelloWorldServlet extends HttpServlet {
    @EJB
    private HelloWorldBean helloWorldBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hello=helloWorldBean.sayHello();
        request.setAttribute("hello",hello);
        request.getRequestDispatcher("hello.jsp").forward(request,response);
    }
}
