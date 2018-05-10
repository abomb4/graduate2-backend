package org.wlyyy.itrs.domain;

import java.util.Date;

public class EmailLog {

    private Long id;
    private Long applyFlowId;
    private String sendEmail;
    private String receiveEmail;
    private String status;
    private String content;
    private Date gmtCreate;
    private Date gmtModify;

    public EmailLog(Long applyFlowId, String sendEmail, String receiveEmail, String status, String content) {
        this.applyFlowId = applyFlowId;
        this.sendEmail = sendEmail;
        this.receiveEmail = receiveEmail;
        this.status = status;
        this.content = content;
    }

    public EmailLog() {
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}
