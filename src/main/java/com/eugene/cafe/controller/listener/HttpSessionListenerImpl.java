package com.eugene.cafe.controller.listener;

import com.eugene.cafe.controller.command.RequestAttribute;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.jsp.jstl.core.Config;

import java.util.Locale;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
//        Config.set(se.getSession(), Config.FMT_LOCALE, Locale.forLanguageTag("en-US"));
        se.getSession().setAttribute(RequestAttribute.LOCALE, "ru-RU");
        System.out.println("session created");
    }
}
