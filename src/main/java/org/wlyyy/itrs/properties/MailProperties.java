package org.wlyyy.itrs.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("mail")
public class MailProperties {

    @Value("${mail.username}")
    private String smtpFrom;

    @Value("${mail.password}")
    private String smtpPasswrod;

    private Integer smtpPort = 587;
    private String protocol = "smtp";
    private Boolean smtpSslEnable = true;
    private Integer smtpTimeout = 7000;
    private Integer smtpConnectiontimeout = 7000;
    private Boolean smtpStarttlsEnable = false;

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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Boolean getSmtpSslEnable() {
        return smtpSslEnable;
    }

    public void setSmtpSslEnable(Boolean smtpSslEnable) {
        this.smtpSslEnable = smtpSslEnable;
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

    public Boolean getSmtpStarttlsEnable() {
        return smtpStarttlsEnable;
    }

    public void setSmtpStarttlsEnable(Boolean smtpStarttlsEnable) {
        this.smtpStarttlsEnable = smtpStarttlsEnable;
    }
}