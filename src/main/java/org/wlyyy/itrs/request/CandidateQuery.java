package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;

import java.io.File;
import java.util.Date;

public class CandidateQuery {

    private Long id;
    private String name;
    private Integer sex;
    private String phoneNo;
    private String email;
    private Date graduateTimeStart;
    private Date graduateTimeEnd;
    private String degree;
    private String workingPlace;
    private Date gmtCreateStart;
    private Date gmtCreateEnd;
    private Date gmtModifyStart;
    private Date gmtModifyEnd;
    private Sort sort;

    public Long getId() {
        return id;
    }

    public CandidateQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CandidateQuery setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSex() {
        return sex;
    }

    public CandidateQuery setSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public CandidateQuery setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CandidateQuery setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getGraduateTimeStart() {
        return graduateTimeStart;
    }

    public CandidateQuery setGraduateTimeStart(Date graduateTimeStart) {
        this.graduateTimeStart = graduateTimeStart;
        return this;
    }

    public Date getGraduateTimeEnd() {
        return graduateTimeEnd;
    }

    public CandidateQuery setGraduateTimeEnd(Date graduateTimeEnd) {
        this.graduateTimeEnd = graduateTimeEnd;
        return this;
    }

    public String getDegree() {
        return degree;
    }

    public CandidateQuery setDegree(String degree) {
        this.degree = degree;
        return this;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public CandidateQuery setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public CandidateQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public CandidateQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public CandidateQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public CandidateQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public CandidateQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
