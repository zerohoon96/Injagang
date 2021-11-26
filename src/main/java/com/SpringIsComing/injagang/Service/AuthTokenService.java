package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Repository.AuthTokenRepository;
import com.SpringIsComing.injagang.token.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private final EmailService emailService;
    private final JavaMailSender sender;

    public String createEmailAuthToken(String userId, String receiverEmail) {

        AuthToken emailAuthToken = AuthToken.createEmailAuthToken(userId);
        authTokenRepository.save(emailAuthToken);

        String from = "InJaGang@test.com";
        String to = receiverEmail;
        String title = "회원가입 인증 이메일 입니다.";
//        String uri = "http://localhost:8080/confirm-email?token=";
        String content = "<strong>InJaGang</strong>을 방문해주셔서 감사합니다." +
                "<br><br>" +
                "아래의 링크를 통해 인증을 완료해주세요." +
                "<br><br>" +
                "<a href=\"http://localhost:8080/confirm-email?token=" + emailAuthToken.getId() + "\">인증하기</button>";


//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(receiverEmail);
//        mailMessage.setSubject("회원가입 이메일 인증");
//        mailMessage.setText("http://localhost:8080/confirm-email?token="+emailAuthToken.getId());
//        emailService.simpleEmail(mailMessage);

        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(title);
            helper.setFrom(from,"Injagang");
            helper.setText(content,true);
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailAuthToken.getId();
    }

    public String findEmailAuthToken(String userId, String receiverEmail){

        AuthToken emailAuthToken = AuthToken.createEmailAuthToken(userId);
        authTokenRepository.save(emailAuthToken);

        String from = "InJaGang@test.com";
        String to = receiverEmail;
        String title = "InJaGang 계정 정보";
//        String uri = "http://localhost:8080/confirm-email?token=";
        String content = "<strong>InJaGang</strong>을 방문해주셔서 감사합니다." +
                "<br><br>" +
                "아이디 : <strong>" + userId +"</strong>" +
                "<br><br>" +
                "비밀번호를 변경을 원하시면 아래 링크를 통해 비밀번호를 변경해주세요." +
                "<br><br>" +
                "<a href=\"http://localhost:8080/change/password?token=" + emailAuthToken.getId() + "\">비밀번호 재설정</a>";

        System.out.println("시발 = " + content);

//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(receiverEmail);
//        mailMessage.setSubject("아이디 비번 찾기");
//        mailMessage.setText("http://localhost:8080/change/password?token="+emailAuthToken.getId());
//        emailService.simpleEmail(mailMessage);

        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(title);
            helper.setFrom(from,"Injagang");
            helper.setText(content,true);
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return emailAuthToken.getId();

    }



}
