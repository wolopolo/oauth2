package com.wolopolo.oauth2.service.implment;

import com.wolopolo.oauth2.dto.account.AccountRegisterReq;
import com.wolopolo.oauth2.dto.account.AccountResp;
import com.wolopolo.oauth2.dto.account.AccountSearchReq;
import com.wolopolo.oauth2.dto.account.ChangePasswordReq;
import com.wolopolo.oauth2.dto.account.ForgotPasswordReq;
import com.wolopolo.oauth2.entity.Account;
import com.wolopolo.oauth2.entity.VerificationToken;
import com.wolopolo.oauth2.event.ForgotPasswordEvent;
import com.wolopolo.oauth2.event.RegistrationCompleteEvent;
import com.wolopolo.oauth2.exception.BusinessException;
import com.wolopolo.oauth2.mapper.AccountMapper;
import com.wolopolo.oauth2.repository.AccountRepo;
import com.wolopolo.oauth2.repository.VerificationTokenRepo;
import com.wolopolo.oauth2.repository.specification.AccountSpecification;
import com.wolopolo.oauth2.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final VerificationTokenRepo verificationTokenRepo;

    @Override
    @Transactional
    public AccountResp save(@Valid AccountRegisterReq accountRegisterReq) {
        Account account = AccountMapper.convert(accountRegisterReq);

        if(accountRepo.findById(account.getEmail()).isPresent()) {
            throw new BusinessException("error.duplicate", "This email has been registered!");
        }

        account.setPassword(passwordEncoder.encode(accountRegisterReq.getPassword()));

        accountRepo.save(account);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(account.getEmail()));

        return AccountMapper.convert(account);
    }

    @Override
    public AccountResp getDetail(String email) {
        return AccountMapper.convert(accountRepo.findById(email).orElseThrow());
    }

    @Override
    public Page<AccountResp> search(AccountSearchReq searchRequest) {
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize(),
                Sort.by(Sort.Direction.valueOf(searchRequest.getSortType()), searchRequest.getSortBy()));
        return accountRepo.findAll(AccountSpecification.search(searchRequest), pageable.previousOrFirst())
                .map(AccountMapper::convert);
    }

    @Override
    public AccountResp update(AccountSearchReq searchRequest) {
        return null;
    }

    @Transactional
    @Override
    public boolean verify(String principal, String token) {
        Optional<VerificationToken> tokenOpt = verificationTokenRepo.findById(token);
        if(tokenOpt.isPresent()) {
            VerificationToken verificationToken = tokenOpt.get();
            if(principal.equals(verificationToken.getEmail())
                    && LocalDateTime.now().isBefore(verificationToken.getExpiryTime())) {
                Account account = accountRepo.findById(principal).orElseThrow();
                account.setVerified(true);
                return true;
            }
        }

        return false;
    }

    @Override
    public void requestForgotPassword(String email) {
        Optional<Account> accountOpt = accountRepo.findById(email);
        if(accountOpt.isEmpty()) {
            throw new BusinessException("error.invalidemail", "This email has not been registered!");
        } else {
            applicationEventPublisher.publishEvent(new ForgotPasswordEvent(accountOpt.get().getEmail()));
        }
    }

    @Transactional
    @Override
    public void forgotPassword(ForgotPasswordReq forgotPasswordReq) {
        Optional<VerificationToken> tokenOpt = verificationTokenRepo.findById(forgotPasswordReq.getToken());
        if(tokenOpt.isPresent()) {
            VerificationToken verificationToken = tokenOpt.get();
            if(LocalDateTime.now().isBefore(verificationToken.getExpiryTime())) {
                Account account = accountRepo.findById(verificationToken.getEmail()).orElseThrow();
                account.setPassword(passwordEncoder.encode(forgotPasswordReq.getNewPassword()));
                return;
            }
        }

        throw new BusinessException("error.forgotpassword", "Password reset failed. Please try again.");
    }

    @Transactional
    @Override
    public void changePassword(String principal, ChangePasswordReq changePasswordReq) {
        Account account = accountRepo.findById(principal).orElseThrow();

        if(!passwordEncoder.matches(changePasswordReq.getOldPassword(), account.getPassword())) {
            throw new BusinessException("error.invalidpassword", "Invalid password");
        }

        if(passwordEncoder.matches(changePasswordReq.getNewPassword(), account.getPassword())
                || (account.getOldPassword() != null && passwordEncoder.matches(changePasswordReq.getNewPassword(), account.getOldPassword()))) {
            throw new BusinessException("error.duplicatepassword", "The new password must not be the same as the two most recent old passwords");
        }

        account.setOldPassword(account.getPassword());
        account.setPassword(passwordEncoder.encode(changePasswordReq.getNewPassword()));
    }

    @Transactional
    @Override
    public void changeActiveStatus(String email) {
        Account account = accountRepo.findById(email).orElseThrow();

        account.setActive(!account.isActive());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepo.findById(email).orElseThrow();
    }
}
