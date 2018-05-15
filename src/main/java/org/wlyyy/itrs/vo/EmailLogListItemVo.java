package org.wlyyy.itrs.vo;

import org.wlyyy.itrs.domain.EmailLog;

import java.text.SimpleDateFormat;

/**
 * 展示层邮件日志表
 */
public class EmailLogListItemVo {

    public static EmailLogListItemVo buildFromDomain(EmailLog source) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Long id = source.getId();
        final Long applyFlowId = source.getApplyFlowId();
        final String sendEmail = source.getSendEmail();
        final String receiveEmail = source.getReceiveEmail();
        final String status = source.getStatus();
        final String simpleStatus = source.getStatus().split(",")[0];
        final String content = source.getContent();
        final String gmtCreate = formatter.format(source.getGmtCreate());
        final String gmtModify = formatter.format(source.getGmtModify());

        return new EmailLogListItemVo(id, applyFlowId, sendEmail, receiveEmail, status, simpleStatus, content, gmtCreate, gmtModify);
    }

    private EmailLogListItemVo() {
    }

    private EmailLogListItemVo(
            Long id, Long applyFlowId, String sendEmail, String receiveEmail,
            String status, String simpleStatus, String content, String gmtCreate,
            String gmtModify
    ) {
        this.id = id;
        this.applyFlowId = applyFlowId;
        this.sendEmail = sendEmail;
        this.receiveEmail = receiveEmail;
        this.status = status;
        this.simpleStatus = simpleStatus;
        this.content = content;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    private Long id;
    private Long applyFlowId;
    private String sendEmail;
    private String receiveEmail;
    private String status;
    private String simpleStatus;
    private String content;
    private String gmtCreate;
    private String gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplyFlowId() {
        return applyFlowId;
    }

    public void setApplyFlowId(Long applyFlowId) {
        this.applyFlowId = applyFlowId;
    }

    public String getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSimpleStatus() {
        return simpleStatus;
    }

    public void setSimpleStatus(String simpleStatus) {
        this.simpleStatus = simpleStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(String gmtModify) {
        this.gmtModify = gmtModify;
    }
}
