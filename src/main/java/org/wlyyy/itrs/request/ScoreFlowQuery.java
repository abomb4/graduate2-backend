package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;

import java.util.Date;

public class ScoreFlowQuery {

    private Long id;
    private Long userId;
    private Integer score;
    private Date gmtCreateStart;
    private Date gmtCreateEnd;
    private Date gmtModifyStart;
    private Date gmtModifyEnd;
    private Sort sort;

    public Long getId() {
        return id;
    }

    public ScoreFlowQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ScoreFlowQuery setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public ScoreFlowQuery setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public ScoreFlowQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public ScoreFlowQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public ScoreFlowQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public ScoreFlowQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public ScoreFlowQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
