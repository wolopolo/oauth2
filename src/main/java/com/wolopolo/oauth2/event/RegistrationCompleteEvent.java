package com.wolopolo.oauth2.event;

import org.springframework.context.ApplicationEvent;

public class RegistrationCompleteEvent extends ApplicationEvent {
    public RegistrationCompleteEvent(String email) {
        super(email);
    }
}
