package org.wlyyy.itrs.spring;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class asdf {

    public static void main(String[] args) throws MessagingException, IOException {
//        final String from = "wlyyyitrs@outlook.com";
//        Properties properties = new Properties();
//        properties.put("mail.smtp.from", from);
//        properties.put("mail.smtp.host", "smtp.office365.com");
//        properties.put("mail.smtp.port", 587);
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.starttls.enable", "true");
//
//        Authenticator authenticator = new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, "cao12345");//发件人的用户名和第三方登录授权码
//            }
//        };
//
//        Session session = Session.getInstance(properties, authenticator);//创建连接会话
//
//        MimeMessage message = new MimeMessage(session);//创建消息
//
//        MimeMultipart mimeMultipart = new MimeMultipart();//创建多条消息内容容器
//
//        MimeBodyPart part01 = new MimeBodyPart();//创建内容
//        part01.setContent("bucibucibucibuci.cn", "text/html;charset=utf-8");
//        mimeMultipart.addBodyPart(part01);//添加内容到容器
//
//        message.setContent(mimeMultipart);//把多条消息装进要发送消息里面
//        message.setSubject("sdfadfssdfaadsffdsaasdf");//设置邮件标题
//        message.setFrom(new InternetAddress(from));//设置发件人
//        message.setRecipients(MimeMessage.RecipientType.BCC, "abomb4@163.com");//设置收件人，并且发送方式为密送,BBC密送，CC抄送，TO正常发送，多个收件人用逗号隔开，在收件人字符串里面
//
//        System.out.println("SEND!!!");
//        Transport.send(message);//发送邮件
//        System.out.println("发送成功!!!");

        final String from = "wlyyyitrs@163.com";
        final String password = "cao12345";
        final String host = "smtp.163.com";
        final String prot = "smtp";
        final int port = 587;

        final String to = "abomb4@163.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.from", from);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.timeout", 7000);
        properties.put("mail.smtp.connectiontimeout", 7000);
        properties.put("mail.smtp.ssl.enable", "true");
        // properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, null); // 创建连接会话

        MimeMessage message = new MimeMessage(session); // 创建消息
        message.setFrom(new InternetAddress(from)); // 设置发件人
        message.setSubject("测试邮件2"); // 设置邮件标题
        message.setRecipients(MimeMessage.RecipientType.BCC, to); // 设置收件人，并且发送方式为密送,BBC密送，CC抄送，TO正常发送，多个收件人用逗号隔开，在收件人字符串里面

        MimeMultipart mimeMultipart = new MimeMultipart(); // 创建多条消息内容容器

        // 创建内容
        MimeBodyPart part01 = new MimeBodyPart();
        part01.setContent("java api 经常无法使用，不能试试吗？", "text/html;charset=utf-8");
        mimeMultipart.addBodyPart(part01);

        message.setContent(mimeMultipart); // 把多条消息装进要发送消息里面

        System.out.println("SEND!!!");
        // send the thing off
        /*
         * The simple way to send a message is this:
         * Transport.send(msg);
         *
         * But we're going to use some SMTP-specific features for
         * demonstration purposes so we need to manage the Transport
         * object explicitly.
         */
        SMTPTransport t = (SMTPTransport) session.getTransport(prot);
        try {
            t.connect(host, port, from, password);
            t.sendMessage(message, message.getAllRecipients());
        } finally {
            System.out.println("Response: " + t.getLastServerResponse());
            t.close();
        }
        System.out.println("发送成功!!!");
    }
}
