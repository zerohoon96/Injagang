package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Repository.AuthTokenRepository;
import com.SpringIsComing.injagang.token.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private final EmailService emailService;

    public String createEmailAuthToken(String userId, String receiverEmail){

        AuthToken emailAuthToken = AuthToken.createEmailAuthToken(userId);
        authTokenRepository.save(emailAuthToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("http://localhost:8080/confirm-email?token="+emailAuthToken.getId());
        emailService.simpleEmail(mailMessage);

        return emailAuthToken.getId();
    }

    public String findEmailAuthToken(String userId, String receiverEmail){

        AuthToken emailAuthToken = AuthToken.createEmailAuthToken(userId);
        authTokenRepository.save(emailAuthToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("아이디 비번 찾기");
        mailMessage.setText("http://localhost:8080/change/password?token="+emailAuthToken.getId());
        emailService.simpleEmail(mailMessage);

        return emailAuthToken.getId();

    }



}
