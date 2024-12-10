package org.romanzhula.mail_dispatcher.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.romanzhula.data_jpa.objects.MailDataJpaDataModule;
import org.romanzhula.mail_dispatcher.services.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.util.Objects;
import java.util.Properties;

@Log4j
@RequiredArgsConstructor
@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.activation.uri}")
    private String activationUri;

    @Value("${spring.mail.password}")
    private String password;

//    @Value("${spring.mail.properties.mail.smtp.auth}")
//    private String auth;
//
//    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
//    private String starttlsEnable;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${mail.debug}")
    private String debug;

//    @Value("${spring.mail.properties.mail.smtp.ssl.trust}")
//    private String sslTrustHost;

    @Override
    public void send(MailDataJpaDataModule mailData) {
//        validateMailProperties();

        JavaMailSenderImpl javaMailSender = configureMailSender();

        String subject = "Activation your account.";
        var messageBody = getActivationMailBody(mailData.getId());
        var emailTo = mailData.getEmailAddress();
        log.debug(String.format("Sending email for mail=[%s]", emailTo));

        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        try {
            javaMailSender.send(mailMessage);
            log.info("Email sent successfully to " + emailTo);
        } catch (Exception e) {
            log.error("Failed to send email to " + emailTo, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private JavaMailSenderImpl configureMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties javaMailProperties = javaMailSender.getJavaMailProperties();

        javaMailProperties.setProperty("mail.transport.protocol", protocol);
        javaMailProperties.setProperty("mail.debug", debug);

        return javaMailSender;
    }

    private String getActivationMailBody(String id) {
        return String.format("Follow the link to complete registration:\n%s", activationUri.replace("{id}", id));
    }
//
//    public void validateMailProperties() {
//        if (Objects.isNull(host) || host.trim().isEmpty()) {
//            throw new IllegalArgumentException("Mail host must be provided");
//        }
//
//        if (port <= 0 || port > 65535) {
//            throw new IllegalArgumentException("Invalid port number: " + port);
//        }
//
//        if (Objects.isNull(username) || username.trim().isEmpty()) {
//            throw new IllegalArgumentException("Mail username must be provided");
//        }
//
//        if (Objects.isNull(password) || password.trim().isEmpty()) {
//            throw new IllegalArgumentException("Mail password must be provided");
//        }
//
//        if (Objects.isNull(auth) || auth.trim().isEmpty()) {
//            throw new IllegalArgumentException("SMTP authentication must be enabled (true/false)");
//        }
//
//        if (Objects.isNull(starttlsEnable) || starttlsEnable.trim().isEmpty()) {
//            throw new IllegalArgumentException("SMTP StartTLS must be enabled (true/false)");
//        }
//
//        if (Objects.isNull(protocol) || protocol.trim().isEmpty()) {
//            throw new IllegalArgumentException("Mail transport protocol must be specified");
//        }
//
//        if (Objects.isNull(sslTrustHost) || sslTrustHost.trim().isEmpty()) {
//            throw new IllegalArgumentException("SSL trust host must be specified if using SSL");
//        }
//    }

}
