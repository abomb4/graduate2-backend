package org.wlyyy.itrs.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class JavaMailConfig {

    private static final int PORT = 465; // 587/465

    @Value("${mail.host}")
    private String host;

    @Value("${mail.smtp}")
    private String smtp;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.defaultEncoding}")
    private String defaultEncoding;


    // 邮件发送器
    // 这个发送不成功，暂时不用了
//    @Bean
//    public JavaMailSender mailSender() {
//        System.err.println("初始化JavaMailSender成功");
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(host);
//        mailSender.setProtocol(smtp);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//        mailSender.setPort(port);
//        mailSender.setDefaultEncoding(defaultEncoding);
//        Properties prop = new Properties();
//        // 设定properties
//        prop.put("mail.smtp.auth", true);
//        // prop.put("mail.smtp.timeout", "25000");
//        // 设置调试模式可以在控制台查看发送过程
//        // prop.put("mail.debug", "true");
////        prop.put("mail.smtp.ssl.enable", "true");
////        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        prop.put("mail.smtp.starttls.enable", true);
//        mailSender.setJavaMailProperties(prop);
//        return mailSender;
//    }

    // 模板视图解析器
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine springTemplateEngine)
    {
        System.out.println("初始化viewResolver成功");
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine);
        return viewResolver;
    }

    // 模板解析器
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver()
    {
        System.err.println("ClassLoaderTemplateResolver");
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("mail/");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(1);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    // 装配TemplateEngine
    @Bean
    public TemplateEngine templateEngine(TemplateResolver templateResolver)
    {
        System.err.println("初始化TemplateEngine成功");
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

}