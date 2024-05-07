package com.wolopolo.oauth2.event.handler;

import com.wolopolo.oauth2.entity.VerificationToken;
import com.wolopolo.oauth2.event.ForgotPasswordEvent;
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
public class ForgotPasswordListener implements ApplicationListener<ForgotPasswordEvent> {
    private final VerificationTokenRepo verificationTokenRepo;
    private final Notifier notifier;

    @Async
    @Transactional
    @Override
    public void onApplicationEvent(ForgotPasswordEvent event) {
        String token = UUID.randomUUID().toString();
        verificationTokenRepo.save(new VerificationToken(token, (String) event.getSource(), LocalDateTime.now().plusMinutes(30)));

        Context context = new Context();
        context.setVariable("token", token);
        String body = notifier.getTextBodyFromTemplate("/email/forgotpassword.html", context);

        notifier.pushNotification((String) event.getSource(), "OAuth2: Request forgot password", body, null);
    }
}
