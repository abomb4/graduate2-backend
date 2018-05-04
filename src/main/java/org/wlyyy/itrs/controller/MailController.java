package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.wlyyy.common.domain.BaseRestResponse;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
public class MailController {

    @Autowired
    protected JavaMailSender javaMailSender;

    @Autowired
    protected TemplateEngine thymeleaf;

    @RequestMapping(value = "/sendTestEmail", method = RequestMethod.GET)
    @ResponseBody
    public BaseRestResponse<Boolean> sendTestEmail(@RequestParam("messagepayload") String messagepayload) throws MessagingException {
        System.out.println(messagepayload);
        boolean result = emailModeHtmlMail("wlyyyitrs@outlook.com","emailEngine","哈哈哈哈哈哈哈哈");
        return new BaseRestResponse<>(true, "发送邮件成功", result);
    }

    /**
     * 发送html邮件
     * @param to 收件人
     * @param subject 主题
     * @param strText 模板文本
     * @return
     */
    protected boolean emailModeHtmlMail(String to, String subject, String strText){
        boolean result = true;

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
        Context con = new Context();
        con.setVariable("hehe", strText);
        String emailText = thymeleaf.process("emailmode.html", con);

        try {
            // 设置收件人，寄件人 用数组发送多个邮件
            messageHelper.setTo(to);
            messageHelper.setFrom(to);
            messageHelper.setSubject(subject);
            // true 表示启动HTML格式的邮件
            messageHelper.setText(emailText, true);
            // 发送邮件
            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
