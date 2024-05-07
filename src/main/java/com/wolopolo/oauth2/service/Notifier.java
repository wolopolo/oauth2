package com.wolopolo.oauth2.service;

import org.thymeleaf.context.IContext;

public interface Notifier {

    void pushNotification(String to, String subject, String text, String[] attachments);

    String getTextBodyFromTemplate(String template, IContext context);
}
