package org.wlyyy.itrs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("mail")
public class MailProperties {

    private String smtpFrom;
    private String smtpPasswrod;
    private Integer smtpPort = 587;
    private String smtpHost = "smtp.163.com";
    private String protocol = "smtp";
    private String smtpAuth = "true";
    private String smtpSslEnable = "true";
    private Integer smtpTimeout = 7000;
    private Integer smtpConnectiontimeout = 7000;
    private String smtpStarttlsEnable = "false";

    public String getSmtpFrom() {
        return smtpFrom;
    }

    public void setSmtpFrom(String smtpFrom) {
        this.smtpFrom = smtpFrom;
    }

    public String getSmtpPasswrod() {
        return smtpPasswrod;
    }

    public void setSmtpPasswrod(String smtpPasswrod) {
        this.smtpPasswrod = smtpPasswrod;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getSmtpTimeout() {
        return smtpTimeout;
    }

    public void setSmtpTimeout(Integer smtpTimeout) {
        this.smtpTimeout = smtpTimeout;
    }

    public Integer getSmtpConnectiontimeout() {
        return smtpConnectiontimeout;
    }

    public void setSmtpConnectiontimeout(Integer smtpConnectiontimeout) {
        this.smtpConnectiontimeout = smtpConnectiontimeout;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(String smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    public String getSmtpSslEnable() {
        return smtpSslEnable;
    }

    public void setSmtpSslEnable(String smtpSslEnable) {
        this.smtpSslEnable = smtpSslEnable;
    }

    public String getSmtpStarttlsEnable() {
        return smtpStarttlsEnable;
    }

    public void setSmtpStarttlsEnable(String smtpStarttlsEnable) {
        this.smtpStarttlsEnable = smtpStarttlsEnable;
    }
}