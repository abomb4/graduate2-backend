package org.wlyyy.itrs.request.rest;

import org.wlyyy.itrs.domain.Candidate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CandidateRequest {

    private String name;
    private Integer sex;
    private String phoneNo;
    private String email;
    private String graduateTime;
    private String degree;
    private String workingPlace;
    private String memo;
    private String attachment;    // 附件

    public Candidate buildCandidate() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new Candidate(null, name, sex, phoneNo, email, format.parse(graduateTime), degree, workingPlace, memo, attachment, null, null);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse graduateTime.", e);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
