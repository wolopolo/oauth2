package com.wolopolo.oauth2.event.handler;

import com.wolopolo.oauth2.entity.VerificationToken;
import com.wolopolo.oauth2.event.RegistrationCompleteEvent;
import com.wolopolo.oauth2.repository.VerificationTokenRepo;
import com.wolopolo.oauth2.service.Notifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final Notifier notifier;
    private final VerificationTokenRepo verificationTokenRepo;

    @Async
    @Transactional
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        String token = UUID.randomUUID().toString();
        verificationTokenRepo.save(new VerificationToken(token, (String) event.getSource(), LocalDateTime.now().plusDays(1)));

        Context context = new Context();
        context.setVariable("token", token);
        String body = notifier.getTextBodyFromTemplate("/email/verify", context);

        notifier.pushNotification((String) event.getSource(), "OAuth2: Please verify your account", body, null);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
