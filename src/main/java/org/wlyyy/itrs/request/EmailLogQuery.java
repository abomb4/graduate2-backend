package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;

import java.util.Date;

public class EmailLogQuery {

    private Long id;
    private Long applyFlowId;
    private String sendEmail;
    private String receiveEmail;
    private String status;
    private String content;
    private Date gmtCreateStart;
    private Date gmtCreateEnd;
    private Date gmtModifyStart;
    private Date gmtModifyEnd;
    private Sort sort;

    public Long getId() {
        return id;
    }

    public EmailLogQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getApplyFlowId() {
        return applyFlowId;
    }

    public EmailLogQuery setApplyFlowId(Long applyFlowId) {
        this.applyFlowId = applyFlowId;
        return this;
    }

    public String getSendEmail() {
        return sendEmail;
    }

    public EmailLogQuery setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
        return this;
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public EmailLogQuery setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public EmailLogQuery setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getContent() {
        return content;
    }

    public EmailLogQuery setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public EmailLogQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public EmailLogQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public EmailLogQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public EmailLogQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public EmailLogQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
