package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

@Service
public class EmailEventListenerService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.username}")
    private String sender;

//    @EventListener()
//    public void handleOrderCreatedEvent(ApplyFlowEvent event) throws Exception {
//        mailSender.send(packageMail());
//    }

    public MimeMessage packageMail() throws Exception {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(sender);
        helper.setTo("abomb4@163.com");
        helper.setText("Thank you for ordering!");
        message.setContent("动词大慈？", "text/html;charset=utf-8");
        return message;
    }

    public static void main(String[] args) throws MessagingException, IOException {
        final String from = "wlyyyitrs@outlook.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.from", from);
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", true);
        // properties.put("mail.smtp.timeout", 7000);
        // Sproperties.put("mail.smtp.connectiontimeout", 7000);
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "cao12345");//发件人的用户名和第三方登录授权码
            }
        };

        Session session = Session.getInstance(properties, authenticator);//创建连接会话

        MimeMessage message = new MimeMessage(session);//创建消息

        MimeMultipart mimeMultipart = new MimeMultipart();//创建多条消息内容容器

        MimeBodyPart part01 = new MimeBodyPart();//创建内容
        part01.setContent("Hi, This is a test mail", "text/html;charset=utf-8");
        mimeMultipart.addBodyPart(part01);//添加内容到容器

        message.setContent(mimeMultipart);//把多条消息装进要发送消息里面
        message.setSubject("Test mail from ITRS");//设置邮件标题
        message.setFrom(new InternetAddress(from));//设置发件人
        message.setRecipients(MimeMessage.RecipientType.BCC, "abomb4@163.com");//设置收件人，并且发送方式为密送,BBC密送，CC抄送，TO正常发送，多个收件人用逗号隔开，在收件人字符串里面

        System.out.println("SEND!!!");
        Transport.send(message);//发送邮件
        System.out.println("发送成功!!!");
    }
}
