package com.wolopolo.oauth2.event;

import org.springframework.context.ApplicationEvent;

public class ForgotPasswordEvent extends ApplicationEvent {
    public ForgotPasswordEvent(String source) {
        super(source);
    }
}
