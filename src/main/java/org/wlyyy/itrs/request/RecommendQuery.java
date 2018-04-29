package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;

import java.util.Date;

public class RecommendQuery {

    private Long id;
    private Long candidateId;
    private Long userId;
    private java.util.Date gmtCreateStart;
    private java.util.Date gmtCreateEnd;
    private java.util.Date gmtModifyStart;
    private java.util.Date gmtModifyEnd;
    private Sort sort;

    public Long getId() {
        return id;
    }

    public RecommendQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public RecommendQuery setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public RecommendQuery setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public RecommendQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public RecommendQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public RecommendQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public RecommendQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public RecommendQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
