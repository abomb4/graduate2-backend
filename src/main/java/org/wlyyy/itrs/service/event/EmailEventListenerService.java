package org.wlyyy.itrs.service.event;

import com.sun.mail.smtp.SMTPTransport;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.domain.EmailLog;
import org.wlyyy.itrs.event.ApplyFlowEvent;
import org.wlyyy.itrs.properties.MailProperties;
import org.wlyyy.itrs.service.CandidateService;
import org.wlyyy.itrs.service.EmailLogService;
import org.wlyyy.itrs.service.UserService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

/**
 * 监听ApplyFlowEvent的变化，进行邮件通知
 */
@Component
public class EmailEventListenerService {

    private String protocol;
    private String password;

    private Properties properties = new Properties();

    @Autowired
    private UserService userService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private EmailLogService emailLogService;

    @Autowired
    protected TemplateEngine thymeleaf;

    public EmailEventListenerService(MailProperties mailProperties) {
        this.protocol = mailProperties.getProtocol();
        this.password = mailProperties.getSmtpPasswrod();
        this.properties.put("mail.smtp.from", mailProperties.getSmtpFrom());
        this.properties.put("mail.smtp.host", mailProperties.getSmtpHost());
        this.properties.put("mail.smtp.port", mailProperties.getSmtpPort());
        this.properties.put("mail.smtp.auth", mailProperties.getSmtpAuth());
        this.properties.put("mail.smtp.timeout", mailProperties.getSmtpTimeout());
        this.properties.put("mail.smtp.connectiontimeout", mailProperties.getSmtpConnectiontimeout());
        this.properties.put("mail.smtp.ssl.enable", mailProperties.getSmtpSslEnable());
        this.properties.put("mail.smtp.starttls.enable", mailProperties.getSmtpStarttlsEnable());
    }

    @EventListener
    public void handleMailEvent(ApplyFlowEvent event) throws Exception {
        if (StringUtils.isNotBlank(event.getApplyFlow().getCurrentResult())) {
            String add = getAddress(event);
            sendMail(event, this.properties, add);
        }
    }

    @Transactional
    public void sendMail(ApplyFlowEvent event, Properties properties, String address)  throws MessagingException, IOException {
        ApplyFlow applyFlow = event.getApplyFlow();
        // 创建连接会话
        Session session = Session.getInstance(properties, null);

        // 创建消息
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.from")));
        // 设置邮件标题
        message.setSubject("企业人才内推系统-邮件通知");
        // 设置收件人，并且发送方式为密送,BBC密送，CC抄送，TO正常发送
        // 多个收件人用逗号隔开，在收件人字符串里面
        message.setRecipients(MimeMessage.RecipientType.BCC, address);

        // 创建多条消息内容容器
        MimeMultipart mimeMultipart = new MimeMultipart();

        // 创建内容
        MimeBodyPart part01 = new MimeBodyPart();
        // 发送邮件模板的内容
        Context con = setContext(event);

        String emailText = thymeleaf.process("emailmode.html", con);
        // System.out.println(emailText);
        part01.setContent(emailText, "text/html;charset=utf-8");
        mimeMultipart.addBodyPart(part01);

        message.setContent(mimeMultipart); // 把多条消息装进要发送消息里面

        System.out.println("发送邮件!!!");
        SMTPTransport t = (SMTPTransport) session.getTransport(this.protocol);
        try {
            t.connect(properties.get("mail.smtp.host").toString(), Integer.parseInt(properties.get("mail.smtp.port").toString()),
                    properties.get("mail.smtp.from").toString(), this.password);
            t.sendMessage(message, message.getAllRecipients());
        } finally {
            System.out.println("Response: " + t.getLastServerResponse());
            // 记录发送邮件的情况
            Long applyFlowId = applyFlow.getId();
            String sendEmail = properties.get("mail.smtp.from").toString();
            String receiveEmail = address;
            String status = t.getLastServerResponse();
            String content = emailText;
            EmailLog emailLog = new EmailLog(applyFlowId, sendEmail, receiveEmail, status, content);
            emailLogService.insertEmailLog(emailLog);

            t.close();
        }
    }

    public String getAddress(ApplyFlowEvent event) {
        // 得到推荐人id
        Long recommendId = event.getApplyFlow().getUserId();
        // 得到推荐人的邮件地址
        String email = userService.findById(recommendId).getEmail();
        return email;
    }

    // 设置模板引擎中的变量
    public Context setContext(ApplyFlowEvent event) {
        ApplyFlow applyFlow = event.getApplyFlow();
        // 得到推荐人id
        Long recommendId = applyFlow.getUserId();
        // 得到推荐人姓名
        String receivedName = userService.findById(recommendId).getRealName();
        // 得到被推荐人姓名
        Long candidateId = applyFlow.getCandidateId();
        String candidateName =candidateService.findById(candidateId).getName();
        // 得到当前流程结果
        String currentResult = applyFlow.getCurrentResult();

        // 塞入模板引擎中变量值
        Context con = new Context();
        con.setVariable("receivedName", receivedName);
        con.setVariable("candidateName", candidateName);
        con.setVariable("currentResult", currentResult);
        return con;
    }
}
